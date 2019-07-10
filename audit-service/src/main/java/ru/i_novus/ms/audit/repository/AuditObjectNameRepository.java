package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditObjectNameEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditObjectNameRepository extends JpaRepository<AuditObjectNameEntity, UUID> {

    @Query("select id from AuditObjectNameEntity ")
    String[] findAllObjectNameId();

    Optional<AuditObjectNameEntity> findByName(String name);

}
