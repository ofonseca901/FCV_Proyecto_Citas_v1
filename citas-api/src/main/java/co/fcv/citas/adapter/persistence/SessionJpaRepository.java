package co.fcv.citas.adapter.persistence;

import java.time.Instant;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, String> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update SessionEntity s set s.refreshId = :next where s.id = :id and s.refreshId = :old and s.revoked = false and s.expiresAt > :now")
    int rotate(@Param("id") String id, @Param("old") String old, @Param("next") String next, @Param("now") Instant now);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update SessionEntity s set s.revoked = true where s.id = :id and s.userId = :userId")
    int revoke(@Param("id") String id, @Param("userId") Long userId);
}
