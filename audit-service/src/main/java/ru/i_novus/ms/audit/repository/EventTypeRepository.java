package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface EventTypeRepository extends JpaRepository<AuditEventTypeEntity, UUID>, QuerydslPredicateExecutor<AuditEventTypeEntity> {

    @Query("select id from AuditEventTypeEntity ")
    String[] findAllEventTypeId();

    Optional<AuditEventTypeEntity> findByNameAndAuditTypeId(String name, String auditId);
}
