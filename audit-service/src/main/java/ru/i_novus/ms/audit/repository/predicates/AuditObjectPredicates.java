package ru.i_novus.ms.audit.repository.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import ru.i_novus.ms.audit.entity.QAuditObjectEntity;

public final class AuditObjectPredicates {

    private AuditObjectPredicates() {
    }

    public static BooleanExpression inAuditObjectTypes(String[] auditObjectTypes) {
        return QAuditObjectEntity.auditObjectEntity.type.in(auditObjectTypes);
    }
}