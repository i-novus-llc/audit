package ru.i_novus.ms.audit.repository;

import org.springframework.stereotype.Repository;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;

import java.util.stream.Stream;

@Repository
public interface AuditExportRepository {

    Stream<AuditEntity> streamSearch(AuditCriteria auditCriteria);
}
