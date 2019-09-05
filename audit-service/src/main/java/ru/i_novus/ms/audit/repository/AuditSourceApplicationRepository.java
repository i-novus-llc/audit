package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditSourceApplicationRepository extends JpaRepository<AuditSourceApplicationEntity, UUID>, QuerydslPredicateExecutor<AuditSourceApplicationEntity> {

    @Query("select id from AuditSourceApplicationEntity ")
    String[] findAllSourceApplicationId();

    Optional<AuditSourceApplicationEntity> findByCode(String code);
}
