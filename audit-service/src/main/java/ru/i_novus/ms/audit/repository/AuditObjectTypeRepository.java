package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditObjectTypeRepository extends JpaRepository<AuditObjectEntity, UUID> {

    @Query("select id from AuditObjectEntity ")
    String[] findAllObjectTypeId();

    Optional<AuditObjectEntity> findByName(String name);
}
