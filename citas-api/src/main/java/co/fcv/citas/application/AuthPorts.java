package co.fcv.citas.application;

import co.fcv.citas.domain.AuthSession;
import co.fcv.citas.domain.User;
import java.time.Instant;
import java.util.Optional;

public final class AuthPorts {
    private AuthPorts() {}
    public interface Users {
        Optional<User> byEmail(String email);
        Optional<User> byId(Long id);
        User create(User user);
    }
    public interface Sessions {
        void create(AuthSession session);
        Optional<AuthSession> byId(String id);
        boolean rotate(String sessionId, String oldRefreshId, String newRefreshId, Instant now);
        boolean touch(String sessionId, Long userId, Instant now);
        void revoke(String id, Long userId);
    }
    public interface Passwords {
        String hash(String raw);
        boolean matches(String raw, String hash);
    }
    public interface Tokens {
        Pair issue(User user, AuthSession session);
        Claims readRefresh(String token);
    }
    public record Pair(String accessToken, String refreshToken, String tokenType, long expiresIn) {}
    public record Claims(Long userId, String sessionId, String refreshId) {}
}
