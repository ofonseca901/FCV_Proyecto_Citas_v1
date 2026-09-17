package co.fcv.citas.adapter.persistence;

import co.fcv.citas.application.AuthPorts;
import co.fcv.citas.domain.AuthSession;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SessionPersistenceAdapter implements AuthPorts.Sessions {
    private final SessionJpaRepository repository;
    public SessionPersistenceAdapter(SessionJpaRepository repository) { this.repository = repository; }
    public void create(AuthSession session) {
        SessionEntity e = new SessionEntity();
        e.id = session.id(); e.userId = session.userId(); e.refreshId = session.refreshId();
        e.expiresAt = session.expiresAt(); e.revoked = session.revoked(); repository.saveAndFlush(e);
    }
    public Optional<AuthSession> byId(String id) {
        return repository.findById(id).map(e -> new AuthSession(e.id, e.userId, e.refreshId, e.expiresAt, e.revoked));
    }
    public boolean rotate(String id, String old, String next, Instant now) {
        return repository.rotate(id, old, next, now) == 1;
    }
    public void revoke(String id, Long userId) { repository.revoke(id, userId); }
}
