package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.EventTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface EventTypeRepository extends JpaRepository<EventTypeEntity, UUID>, QuerydslPredicateExecutor<EventTypeEntity> {

    @Query("select id from EventTypeEntity ")
    String[] findAllEventTypeId();

    Optional<EventTypeEntity> findByNameAndAuditTypeId(String name, String auditId);
}
