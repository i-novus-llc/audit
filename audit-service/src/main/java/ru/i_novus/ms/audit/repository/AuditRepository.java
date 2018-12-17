package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditEntity;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditEntity, UUID>, QuerydslPredicateExecutor<AuditEntity> {
}
