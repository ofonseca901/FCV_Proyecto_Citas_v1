package co.fcv.citas.application;

import co.fcv.citas.domain.AuthSession;
import co.fcv.citas.domain.User;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import static co.fcv.citas.application.AuthPorts.*;

// Framework-independent use cases. The inbound facade supplies transaction boundaries.
public final class AuthService {
    private final Users users;
    private final Sessions sessions;
    private final Passwords passwords;
    private final Tokens tokens;
    private final Clock clock;
    private final Duration refreshLifetime;
    private final Duration inactivityTimeout;
    private final String dummyHash;

    public AuthService(Users users, Sessions sessions, Passwords passwords, Tokens tokens,
                       Clock clock, Duration refreshLifetime, Duration inactivityTimeout) {
        this.users = users; this.sessions = sessions; this.passwords = passwords;
        this.tokens = tokens; this.clock = clock; this.refreshLifetime = refreshLifetime; this.inactivityTimeout = inactivityTimeout;
        this.dummyHash = passwords.hash(UUID.randomUUID().toString());
    }

    public record Registration(String firstName, String lastName, String documentType,
                               String documentNumber, String email, String phone, String password) {}
    public record Login(Pair tokens, User user) {}

    public User register(Registration input) {
        int bytes = input.password().getBytes(StandardCharsets.UTF_8).length;
        if (input.password().length() < 8 || bytes > 72) {
            throw new AuthFailure(AuthFailure.Kind.INVALID, "La contraseña requiere mínimo 8 caracteres y máximo 72 bytes UTF-8.");
        }
        return users.create(new User(null, input.firstName().strip(), input.lastName().strip(),
                input.documentType(), input.documentNumber().strip(), normalizeEmail(input.email()),
                input.phone().strip(), passwords.hash(input.password()), true, Set.of("USER")));
    }

    public Login login(String email, String password) {
        if (password.getBytes(StandardCharsets.UTF_8).length > 72) throw AuthFailure.unauthorized();
        User user = users.byEmail(normalizeEmail(email)).orElse(null);
        boolean matches = passwords.matches(password, user == null ? dummyHash : user.passwordHash());
        if (!matches || user == null || !user.active()) throw AuthFailure.unauthorized();
        AuthSession session = new AuthSession(UUID.randomUUID().toString(), user.id(),
                UUID.randomUUID().toString(), clock.instant().plus(refreshLifetime), clock.instant(), false);
        sessions.create(session);
        return new Login(tokens.issue(user, session), user);
    }

    public Login refresh(String token) {
        Claims claims = tokens.readRefresh(token);
        AuthSession session = sessions.byId(claims.sessionId()).orElseThrow(AuthFailure::unauthorized);
        if (!session.userId().equals(claims.userId()) || !session.refreshId().equals(claims.refreshId())) throw AuthFailure.unauthorized();
        if (!session.usableAt(clock.instant(), inactivityTimeout)) { sessions.revoke(session.id(), session.userId()); throw AuthFailure.unauthorized(); }
        User user = users.byId(session.userId()).filter(User::active).orElseThrow(AuthFailure::unauthorized);
        String next = UUID.randomUUID().toString();
        if (!sessions.rotate(session.id(), claims.refreshId(), next, clock.instant())) throw AuthFailure.unauthorized();
        return new Login(tokens.issue(user, new AuthSession(session.id(), user.id(), next,
                session.expiresAt(), clock.instant(), false)), user);
    }

    public User identity(Long userId, String sessionId) {
        AuthSession session = sessions.byId(sessionId).orElseThrow(AuthFailure::unauthorized);
        if (!session.userId().equals(userId)) throw AuthFailure.unauthorized();
        if (!session.usableAt(clock.instant(), inactivityTimeout)) { sessions.revoke(session.id(), userId); throw AuthFailure.unauthorized(); }
        if (!sessions.touch(session.id(), userId, clock.instant())) throw AuthFailure.unauthorized();
        return users.byId(userId).filter(User::active).orElseThrow(AuthFailure::unauthorized);
    }

    public void logout(Long userId, String sessionId) { sessions.revoke(sessionId, userId); }
    private String normalizeEmail(String email) { return email.strip().toLowerCase(Locale.ROOT); }
}
