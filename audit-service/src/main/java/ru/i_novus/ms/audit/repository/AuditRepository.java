package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.i_novus.ms.audit.entity.AuditEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditEntity, UUID>, QuerydslPredicateExecutor<AuditEntity> {

    @Query(nativeQuery = true, value = "SELECT * FROM audit.audit WHERE event_date<now() AND event_date>now()-interval '31' day and id=:id")
    Optional<AuditEntity> searchEntityByLastMonth(@Param("id") UUID id);

    Optional<AuditEntity> findFirstByAuditTypeIdAndAuditSourceApplicationOrderByEventDateDesc(Short auditType,
                                                                                              String auditSourceApplication);

    boolean existsByAuditTypeIdAndEventDateAndEventTypeAndUserIdAndAuditSourceApplicationAndContext
            (Short auditTypeId, LocalDateTime eventDate, String eventType, String userId, String auditSourceApplication, String context);

}
