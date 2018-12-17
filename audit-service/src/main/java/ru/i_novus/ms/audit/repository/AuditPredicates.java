package ru.i_novus.ms.audit.repository;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import ru.i_novus.ms.audit.entity.QAuditEntity;

import java.time.LocalDateTime;

public final class AuditPredicates {

    private AuditPredicates() {
    }

    public static BooleanExpression isEventDateAfterOrEquals(LocalDateTime from) {
        return QAuditEntity.auditEntity.eventDate.after(from).or(QAuditEntity.auditEntity.eventDate.eq(from));
    }

    public static BooleanExpression isEventDateBeforeOrEquals(LocalDateTime to) {
        return QAuditEntity.auditEntity.eventDate.before(to).or(QAuditEntity.auditEntity.eventDate.eq(to));
    }

    public static BooleanExpression isEventTypeEquals(String value) {
        return QAuditEntity.auditEntity.eventType.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectTypeEquals(String value) {
        return QAuditEntity.auditEntity.objectType.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectNameEquals(String value) {
        return QAuditEntity.auditEntity.objectName.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectIdEquals(String value) {
        return QAuditEntity.auditEntity.objectId.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isUserIdEquals(String value) {
        return QAuditEntity.auditEntity.userId.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isUsernameEquals(String value) {
        return QAuditEntity.auditEntity.username.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceApplicationContains(String value) {
        return QAuditEntity.auditEntity.sourceApplication.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceWorkstationContains(String value) {
        return QAuditEntity.auditEntity.sourceWorkstation.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression isContextContains(String value) {
        return Expressions.booleanOperation(
                Ops.STRING_CONTAINS_IC,
                Expressions.stringOperation(Ops.STRING_CAST, QAuditEntity.auditEntity.context.stringValue()),
                Expressions.constant(value.trim()));
    }
}