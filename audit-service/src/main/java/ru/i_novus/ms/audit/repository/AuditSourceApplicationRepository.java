package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditSourceApplicationRepository extends JpaRepository<AuditSourceApplicationEntity, UUID> {

    @Query("select id from AuditSourceApplicationEntity ")
    String[] findAllSourceApplicationId();

    Optional<AuditSourceApplicationEntity> findByName(String name);
}
