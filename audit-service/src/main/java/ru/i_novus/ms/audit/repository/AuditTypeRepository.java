package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;

public interface AuditTypeRepository extends JpaRepository<AuditTypeEntity, Short> {
}
