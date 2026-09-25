package co.fcv.citas.domain;

import java.time.Instant;

public record AuthSession(String id, Long userId, String refreshId, Instant expiresAt, Instant lastActivityAt, boolean revoked) {
    public boolean usableAt(Instant now, java.time.Duration inactivityTimeout) {
        return !revoked && expiresAt.isAfter(now) && lastActivityAt.plus(inactivityTimeout).isAfter(now);
    }
}
