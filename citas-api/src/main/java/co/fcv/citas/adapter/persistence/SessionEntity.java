package co.fcv.citas.adapter.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "auth_sessions")
public class SessionEntity {
    @Id @Column(length = 36) public String id;
    @Column(nullable = false) public Long userId;
    @Column(nullable = false, length = 36) public String refreshId;
    @Column(nullable = false) public Instant expiresAt;
    @Column(nullable = false) public Instant lastActivityAt;
    @Column(nullable = false) public boolean revoked;
}
