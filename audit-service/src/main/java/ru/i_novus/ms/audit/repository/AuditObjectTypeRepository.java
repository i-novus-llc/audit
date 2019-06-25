package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditObjectType;

import java.util.Optional;
import java.util.UUID;

public interface AuditObjectTypeRepository extends JpaRepository<AuditObjectType, UUID> {

    @Query("select id from AuditObjectType ")
    String[] findAllObjectTypeId();

    Optional<AuditObjectType> findByName(String name);
}
