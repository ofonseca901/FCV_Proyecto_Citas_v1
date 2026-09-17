package co.fcv.citas.adapter.security;

import co.fcv.citas.application.AuthFailure;
import co.fcv.citas.application.AuthPorts;
import co.fcv.citas.domain.AuthSession;
import co.fcv.citas.domain.User;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
public class JwtTokens implements AuthPorts.Tokens {
    private static final String ISSUER = "fcv-citas-training";
    private final JwtEncoder accessEncoder;
    private final JwtEncoder refreshEncoder;
    private final JwtDecoder accessDecoder;
    private final JwtDecoder refreshDecoder;
    private final Duration lifetime;
    private final Clock clock;

    public JwtTokens(@Value("${app.jwt.access-secret}") String access,
                     @Value("${app.jwt.refresh-secret}") String refresh,
                     @Value("${app.jwt.access-minutes}") long minutes, Clock clock) {
        if (access.getBytes(StandardCharsets.UTF_8).length < 32 || refresh.getBytes(StandardCharsets.UTF_8).length < 32
                || access.equals(refresh) || access.contains("CHANGE_ME") || refresh.contains("CHANGE_ME")) {
            throw new IllegalArgumentException("Configure dos secretos JWT distintos de al menos 32 bytes.");
        }
        if (minutes < 1 || minutes > 60) throw new IllegalArgumentException("JWT_ACCESS_MINUTES debe estar entre 1 y 60.");
        this.clock = clock; lifetime = Duration.ofMinutes(minutes);
        accessEncoder = encoder(access); refreshEncoder = encoder(refresh);
        accessDecoder = decoder(access, "access"); refreshDecoder = decoder(refresh, "refresh");
    }
    private JwtEncoder encoder(String secret) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secret.getBytes(StandardCharsets.UTF_8)));
    }
    private JwtDecoder decoder(String secret, String purpose) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                .macAlgorithm(MacAlgorithm.HS256).build();
        JwtTimestampValidator timestamp = new JwtTimestampValidator(Duration.ZERO);
        timestamp.setClock(clock);
        OAuth2TokenValidator<Jwt> shape = jwt -> {
            try {
                Long.parseLong(jwt.getSubject());
                if (jwt.getExpiresAt() == null || jwt.getIssuedAt() == null || jwt.getIssuedAt().isAfter(clock.instant())
                        || jwt.getId() == null || jwt.getClaimAsString("sid") == null
                        || !purpose.equals(jwt.getClaimAsString("purpose")) || !jwt.getAudience().contains("citas-api")) {
                    throw new IllegalArgumentException();
                }
                return OAuth2TokenValidatorResult.success();
            } catch (RuntimeException e) {
                return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token"));
            }
        };
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(timestamp, new JwtIssuerValidator(ISSUER), shape));
        return decoder;
    }
    public JwtDecoder accessDecoder() { return accessDecoder; }
    public AuthPorts.Pair issue(User user, AuthSession session) {
        Instant now = clock.instant();
        Instant accessExpiry = now.plus(lifetime).isBefore(session.expiresAt()) ? now.plus(lifetime) : session.expiresAt();
        String access = encode(accessEncoder, user, session, "access", java.util.UUID.randomUUID().toString(), now, accessExpiry);
        String refresh = encode(refreshEncoder, user, session, "refresh", session.refreshId(), now, session.expiresAt());
        return new AuthPorts.Pair(access, refresh, "Bearer", Duration.between(now, accessExpiry).toSeconds());
    }
    private String encode(JwtEncoder encoder, User user, AuthSession session, String purpose, String id, Instant now, Instant expiry) {
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer(ISSUER).audience(List.of("citas-api"))
                .subject(user.id().toString()).id(id).issuedAt(now).expiresAt(expiry)
                .claim("sid", session.id()).claim("purpose", purpose).claim("roles", user.roles()).build();
        return encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
    public AuthPorts.Claims readRefresh(String token) {
        try {
            Jwt jwt = refreshDecoder.decode(token);
            return new AuthPorts.Claims(Long.valueOf(jwt.getSubject()), jwt.getClaimAsString("sid"), jwt.getId());
        } catch (RuntimeException e) { throw AuthFailure.unauthorized(); }
    }
}
