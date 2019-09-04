package ru.i_novus.ms.audit.repository.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import ru.i_novus.ms.audit.entity.QEventTypeEntity;

public final class EventTypePredicates {

    private EventTypePredicates() {
    }

    public static BooleanExpression eqAuditTypeId(Short value) {
        return QEventTypeEntity.eventTypeEntity.auditTypeId.eq(value);
    }

    public static BooleanExpression containsName(String value) {
        return QEventTypeEntity.eventTypeEntity.name.contains(value.trim());
    }

}