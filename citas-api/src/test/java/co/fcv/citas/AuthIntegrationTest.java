package co.fcv.citas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:citas;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.username=sa", "spring.datasource.password=", "spring.datasource.driver-class-name=org.h2.Driver"
})
@AutoConfigureMockMvc
class AuthIntegrationTest {
    static final String ACCESS = UUID.randomUUID() + UUID.randomUUID().toString();
    static final String REFRESH = UUID.randomUUID() + UUID.randomUUID().toString();
    @DynamicPropertySource static void secrets(DynamicPropertyRegistry registry) {
        registry.add("app.jwt.access-secret", () -> ACCESS);
        registry.add("app.jwt.refresh-secret", () -> REFRESH);
    }
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired JdbcTemplate jdbc;

    Map<String, String> registration() {
        String id = UUID.randomUUID().toString().replace("-", "");
        return new HashMap<>(Map.of("firstName", "Paciente", "lastName", "Sintético", "documentType", "CC",
                "documentNumber", id.substring(0, 24), "email", id + "@example.test", "phone", "3000000000", "password", "Prueba-S2-2026!"));
    }
    ResultActions postJson(String path, Object body) throws Exception {
        return mvc.perform(post("/api/auth" + path).contentType("application/json").content(json.writeValueAsBytes(body)));
    }
    JsonNode login(Map<String, String> input) throws Exception {
        return json.readTree(postJson("/login", Map.of("email", input.get("email"), "password", input.get("password")))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
    }
    void me(String token, int statusCode) throws Exception {
        mvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + token)).andExpect(status().is(statusCode));
    }

    @Test void registrationPersistsHashAndOnlyUserRole() throws Exception {
        var data = registration();
        data.put("email", data.get("email").toUpperCase(Locale.ROOT));
        postJson("/register", data).andExpect(status().isCreated())
                .andExpect(jsonPath("roles[0]").value("USER"))
                .andExpect(jsonPath("passwordHash").doesNotExist()).andExpect(jsonPath("password").doesNotExist());
        String hash = jdbc.queryForObject("select password_hash from app_users where email = ?", String.class, data.get("email").toLowerCase(Locale.ROOT));
        assertThat(hash).startsWith("$2").isNotEqualTo(data.get("password"));
        data.put("email", data.get("email").toLowerCase(Locale.ROOT));
        postJson("/register", data).andExpect(status().isConflict());
        data.put("email", "other" + data.get("email"));
        postJson("/register", data).andExpect(status().isConflict());
    }

    @Test void validationAndPrivilegeEscalationAreRejected() throws Exception {
        var data = registration();
        data.put("roles", "ADMIN"); postJson("/register", data).andExpect(status().isBadRequest());
        data.remove("roles"); data.put("firstName", " "); postJson("/register", data).andExpect(status().isBadRequest());
        data.put("firstName", "Paciente"); data.put("email", "invalid"); postJson("/register", data).andExpect(status().isBadRequest());
        data.put("email", "synthetic@example.test"); data.put("password", "corta"); postJson("/register", data).andExpect(status().isBadRequest());
        data.put("password", "ñ".repeat(40)); postJson("/register", data).andExpect(status().isBadRequest());
        postJson("/login", Map.of("email", "synthetic@example.test", "password", "ñ".repeat(40)))
                .andExpect(status().isUnauthorized());
    }

    @Test void sessionLifecycleAndTokenSeparation() throws Exception {
        var data = registration(); postJson("/register", data).andExpect(status().isCreated());
        postJson("/login", Map.of("email", data.get("email"), "password", "incorrecta")).andExpect(status().isUnauthorized());
        JsonNode first = login(data), independent = login(data);
        me(first.path("accessToken").asText(), 200);
        me(first.path("refreshToken").asText(), 401);
        me(first.path("accessToken").asText() + "broken", 401);
        postJson("/refresh", Map.of("refreshToken", first.path("accessToken").asText())).andExpect(status().isUnauthorized());
        JsonNode rotated = json.readTree(postJson("/refresh", Map.of("refreshToken", first.path("refreshToken").asText()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        postJson("/refresh", Map.of("refreshToken", first.path("refreshToken").asText())).andExpect(status().isUnauthorized());
        mvc.perform(post("/api/auth/logout").header("Authorization", "Bearer " + rotated.path("accessToken").asText())).andExpect(status().isNoContent());
        me(rotated.path("accessToken").asText(), 401); me(first.path("accessToken").asText(), 401);
        postJson("/refresh", Map.of("refreshToken", rotated.path("refreshToken").asText())).andExpect(status().isUnauthorized());
        me(independent.path("accessToken").asText(), 200);
    }

    @Test void concurrentRefreshHasExactlyOneWinner() throws Exception {
        var data = registration(); postJson("/register", data).andExpect(status().isCreated());
        String token = login(data).path("refreshToken").asText();
        try (var pool = Executors.newFixedThreadPool(2)) {
            CountDownLatch ready = new CountDownLatch(2), go = new CountDownLatch(1);
            Callable<Integer> call = () -> {
                ready.countDown(); go.await();
                return postJson("/refresh", Map.of("refreshToken", token)).andReturn().getResponse().getStatus();
            };
            var a = pool.submit(call); var b = pool.submit(call);
            assertThat(ready.await(5, TimeUnit.SECONDS)).isTrue(); go.countDown();
            assertThat(List.of(a.get(10, TimeUnit.SECONDS), b.get(10, TimeUnit.SECONDS))).containsExactlyInAnyOrder(200, 401);
        }
    }

    @Test void expiredAccessAndMissingAuthenticationAreRejected() throws Exception {
        mvc.perform(get("/api/auth/me")).andExpect(status().isUnauthorized());
        var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(ACCESS.getBytes(StandardCharsets.UTF_8)));
        var claims = JwtClaimsSet.builder().issuer("fcv-citas-training").subject("1").audience(List.of("citas-api"))
                .id(UUID.randomUUID().toString()).issuedAt(Instant.now().minusSeconds(120)).expiresAt(Instant.now().minusSeconds(60))
                .claim("purpose", "access").claim("sid", UUID.randomUUID().toString()).build();
        String expired = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
        me(expired, 401);
    }

    @Test void inactiveSessionIsRevokedAfterTenMinutes() throws Exception {
        var data = registration(); postJson("/register", data).andExpect(status().isCreated());
        JsonNode session = login(data);
        Long userId = jdbc.queryForObject("select id from app_users where email=?", Long.class, data.get("email"));
        String sessionId = jdbc.queryForObject("select id from auth_sessions where user_id=?", String.class, userId);
        jdbc.update("update auth_sessions set last_activity_at=? where id=?", Instant.now().minus(Duration.ofMinutes(11)), sessionId);
        me(session.path("accessToken").asText(), 401);
        assertThat(jdbc.queryForObject("select revoked from auth_sessions where id=?", Boolean.class, sessionId)).isTrue();
    }

    @Test void corsOnlyAllowsConfiguredFrontend() throws Exception {
        mvc.perform(options("/api/auth/login").header("Origin", "http://localhost:5173").header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
        mvc.perform(options("/api/auth/login").header("Origin", "https://untrusted.example").header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isForbidden()).andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
}
