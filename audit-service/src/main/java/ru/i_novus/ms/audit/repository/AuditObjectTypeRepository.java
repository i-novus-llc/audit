package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditObjectTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditObjectTypeRepository extends JpaRepository<AuditObjectTypeEntity, UUID> {

    @Query("select id from AuditObjectTypeEntity ")
    String[] findAllObjectTypeId();

    Optional<AuditObjectTypeEntity> findByName(String name);
}
