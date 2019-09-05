package ru.i_novus.ms.audit.repository.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import ru.i_novus.ms.audit.entity.QAuditEventTypeEntity;

public final class EventTypePredicates {

    private EventTypePredicates() {
    }

    public static BooleanExpression eqAuditTypeId(Short value) {
        return QAuditEventTypeEntity.auditEventTypeEntity.auditTypeId.eq(value);
    }

    public static BooleanExpression containsName(String value) {
        return QAuditEventTypeEntity.auditEventTypeEntity.name.containsIgnoreCase(value.trim());
    }

}