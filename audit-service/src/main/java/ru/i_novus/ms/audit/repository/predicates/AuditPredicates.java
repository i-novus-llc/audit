/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.repository.predicates;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import ru.i_novus.ms.audit.entity.QAuditEntity;

import java.time.LocalDateTime;

public final class AuditPredicates {

    private AuditPredicates() {
    }

    public static BooleanExpression isIdContains(String value) {
        return QAuditEntity.auditEntity.idAsText.containsIgnoreCase(value);
    }

    public static BooleanExpression isEventDateGoe(LocalDateTime from) {
        return QAuditEntity.auditEntity.eventDate.goe(from);
    }

    public static BooleanExpression isEventDateLoe(LocalDateTime to) {
        return QAuditEntity.auditEntity.eventDate.loe(to);
    }

    public static BooleanExpression isEventDateBetween(LocalDateTime from, LocalDateTime to) {
        return QAuditEntity.auditEntity.eventDate.between(from, to);
    }

    public static BooleanExpression isEventTypeEquals(String value) {
        return QAuditEntity.auditEntity.eventType.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isObjectIdEquals(String value) {
        return QAuditEntity.auditEntity.objectId.containsIgnoreCase(value.trim());
    }

    public static BooleanExpression inSourceApplicationNames(String[] names) {
        return QAuditEntity.auditEntity.auditSourceApplication.in(names);
    }

    public static BooleanExpression inObjectTypeNames(String[] names) {
        return QAuditEntity.auditEntity.auditObjectType.in(names);
    }

    public static BooleanExpression inObjectNameNames(String[] names) {
        return QAuditEntity.auditEntity.auditObjectName.in(names);
    }


    public static BooleanExpression isUserIdEquals(String value) {
        return QAuditEntity.auditEntity.userId.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isUsernameEquals(String value) {
        return QAuditEntity.auditEntity.username.containsIgnoreCase(value.trim());
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

    public static BooleanExpression isHostnameContains(String value) {
        return QAuditEntity.auditEntity.hostname.equalsIgnoreCase(value.trim());
    }

    public static BooleanExpression isAuditTypeIdEquals(Short value) {
        return QAuditEntity.auditEntity.auditTypeId.eq(value);
    }

    public static BooleanExpression inSenders(String[] value) {
        return QAuditEntity.auditEntity.senderId.in(value);
    }

    public static BooleanExpression inReceivers(String[] value) {
        return QAuditEntity.auditEntity.receiverId.in(value);
    }

    public static BooleanExpression inEventTypeNames(String[] typeNames) {
        return QAuditEntity.auditEntity.eventType.in(typeNames);
    }

}