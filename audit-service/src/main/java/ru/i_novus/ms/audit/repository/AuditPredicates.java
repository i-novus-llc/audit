package ru.i_novus.ms.audit.repository;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import ru.i_novus.ms.audit.entity.QAuditEnt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class AuditPredicates {

    private AuditPredicates() {
    }

    public static BooleanExpression isEventDateAfterOrEquals(LocalDateTime from) {
        return QAuditEnt.auditEnt.eventDate.after(from).or(QAuditEnt.auditEnt.eventDate.eq(from));
    }

    public static BooleanExpression isEventDateBeforeOrEquals(LocalDateTime to) {
        return QAuditEnt.auditEnt.eventDate.before(to).or(QAuditEnt.auditEnt.eventDate.eq(to));
    }

    public static BooleanExpression inSourceApplicationIds(List<UUID> ids){
        return QAuditEnt.auditEnt.auditSourceApplication.id.in(ids);
    }

    public static BooleanExpression inObjectTypeIds(List<UUID> ids){
        return QAuditEnt.auditEnt.auditObjectType.id.in(ids);
    }

    public static BooleanExpression inObjectNameIds(List<UUID> ids){
        return QAuditEnt.auditEnt.auditObjectName.id.in(ids);
    }

    public static BooleanExpression isEventTypeEquals(String value) {
        return QAuditEnt.auditEnt.eventType.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectTypeEquals(String value) {
        return QAuditEnt.auditEnt.auditObjectType.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectNameEquals(String value) {
        return QAuditEnt.auditEnt.auditObjectName.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectIdEquals(String value) {
        return QAuditEnt.auditEnt.objectId.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression inSourceApplicationNames(String[] names){
        return QAuditEnt.auditEnt.auditSourceApplication.name.in(names);
    }

    public static BooleanExpression inObjectTypeNames(String[] names){
        return QAuditEnt.auditEnt.auditObjectType.name.in(names);
    }

    public static BooleanExpression inObjectNameNames(String[] names){
        return QAuditEnt.auditEnt.auditObjectName.name.in(names);
    }


    public static BooleanExpression isUserIdEquals(String value) {
        return QAuditEnt.auditEnt.userId.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isUsernameEquals(String value) {
        return QAuditEnt.auditEnt.username.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceApplicationContains(String value) {
        return QAuditEnt.auditEnt.auditSourceApplication.name.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isSourceWorkstationContains(String value) {
        return QAuditEnt.auditEnt.sourceWorkstation.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isContextContains(String value) {
        return Expressions.booleanOperation(
                Ops.STRING_CONTAINS_IC,
                Expressions.stringOperation(Ops.STRING_CAST, QAuditEnt.auditEnt.context.stringValue()),
                Expressions.constant(value.trim()));
    }

    public static BooleanExpression isHostnameContains(String value){
        return QAuditEnt.auditEnt.hostname.equalsIgnoreCase(value.trim());
    }
}