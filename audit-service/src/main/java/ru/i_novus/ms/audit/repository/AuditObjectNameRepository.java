package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditObjectName;

import java.util.Optional;
import java.util.UUID;

public interface AuditObjectNameRepository extends JpaRepository<AuditObjectName, UUID> {

    @Query("select id from AuditObjectName ")
    String[] findAllObjectNameId();

    Optional<AuditObjectName> findByName(String name);

}
