package co.fcv.citas.domain;

import java.time.Instant;

public record AuthSession(String id, Long userId, String refreshId, Instant expiresAt, boolean revoked) {
    public boolean usableAt(Instant now) { return !revoked && expiresAt.isAfter(now); }
}
