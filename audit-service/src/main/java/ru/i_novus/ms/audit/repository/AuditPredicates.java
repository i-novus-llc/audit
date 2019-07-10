package ru.i_novus.ms.audit.repository;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import ru.i_novus.ms.audit.entity.QAuditEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class AuditPredicates {

    private AuditPredicates() {
    }

    public static BooleanExpression isEventDateAfterOrEquals(LocalDateTime from) {
        return QAuditEntity.auditEntity.eventDate.after(from).or(QAuditEntity.auditEntity.eventDate.eq(from));
    }

    public static BooleanExpression isEventDateBeforeOrEquals(LocalDateTime to) {
        return QAuditEntity.auditEntity.eventDate.before(to).or(QAuditEntity.auditEntity.eventDate.eq(to));
    }

    public static BooleanExpression inSourceApplicationIds(List<UUID> ids){
        return QAuditEntity.auditEntity.auditSourceApplication.id.in(ids);
    }

    public static BooleanExpression inObjectTypeIds(List<UUID> ids){
        return QAuditEntity.auditEntity.auditObjectType.id.in(ids);
    }

    public static BooleanExpression inObjectNameIds(List<UUID> ids){
        return QAuditEntity.auditEntity.auditObjectName.id.in(ids);
    }

    public static BooleanExpression isEventTypeEquals(String value) {
        return QAuditEntity.auditEntity.eventType.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectTypeEquals(String value) {
        return QAuditEntity.auditEntity.auditObjectType.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectNameEquals(String value) {
        return QAuditEntity.auditEntity.auditObjectName.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectIdEquals(String value) {
        return QAuditEntity.auditEntity.objectId.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression inSourceApplicationNames(String[] names){
        return QAuditEntity.auditEntity.auditSourceApplication.name.in(names);
    }

    public static BooleanExpression inObjectTypeNames(String[] names){
        return QAuditEntity.auditEntity.auditObjectType.name.in(names);
    }

    public static BooleanExpression inObjectNameNames(String[] names){
        return QAuditEntity.auditEntity.auditObjectName.name.in(names);
    }


    public static BooleanExpression isUserIdEquals(String value) {
        return QAuditEntity.auditEntity.userId.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isUsernameEquals(String value) {
        return QAuditEntity.auditEntity.username.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceApplicationContains(String value) {
        return QAuditEntity.auditEntity.auditSourceApplication.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceWorkstationContains(String value) {
        return QAuditEntity.auditEntity.sourceWorkstation.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isContextContains(String value) {
        return Expressions.booleanOperation(
                Ops.STRING_CONTAINS_IC,
                Expressions.stringOperation(Ops.STRING_CAST, QAuditEntity.auditEntity.context.stringValue()),
                Expressions.constant(value.trim()));
    }

    public static BooleanExpression isHostnameContains(String value){
        return QAuditEntity.auditEntity.hostname.equalsIgnoreCase(value.trim());
    }
}