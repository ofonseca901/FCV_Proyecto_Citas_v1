package co.fcv.citas.adapter.security;

import co.fcv.citas.application.AuthFailure;
import co.fcv.citas.adapter.web.AuthFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

@Configuration @EnableMethodSecurity
public class SecurityConfiguration {
    @Bean SecurityFilterChain security(HttpSecurity http, JwtTokens tokens, AuthFacade facade) throws Exception {
        return http.csrf(csrf -> csrf.disable()).cors(cors -> {})
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/auth/register", "/api/auth/login", "/api/auth/refresh").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/catalogs/**", "/api/availability").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/actuator/health").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(o -> o.jwt(j -> j.decoder(tokens.accessDecoder()).jwtAuthenticationConverter(jwt -> {
                    try {
                        var user = facade.identity(Long.valueOf(jwt.getSubject()), jwt.getClaimAsString("sid"));
                        var authorities = user.roles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList();
                        return new JwtAuthenticationToken(jwt, authorities);
                    } catch (AuthFailure | IllegalArgumentException e) {
                        throw new BadCredentialsException("Sesión inválida.");
                    }
                })).authenticationEntryPoint((request, response, failure) -> {
                    response.setStatus(401); response.setContentType("application/json;charset=UTF-8");
                    response.setHeader("WWW-Authenticate", "Bearer");
                    response.getWriter().write("{\"code\":\"UNAUTHORIZED\",\"message\":\"Credenciales o sesión inválidas.\"}");
                }))
                .build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.frontend-origin}") String origin) {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(List.of(origin)); c.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        c.setAllowedHeaders(List.of("Authorization", "Content-Type")); c.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**", c);
        return source;
    }
}
