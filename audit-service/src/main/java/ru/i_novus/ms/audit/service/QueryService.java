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

package ru.i_novus.ms.audit.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.entity.QAuditObjectEntity;
import ru.i_novus.ms.audit.entity.QAuditSourceApplicationEntity;
import ru.i_novus.ms.audit.repository.predicates.EventTypePredicates;

import java.util.List;

import static ru.i_novus.ms.audit.repository.predicates.AuditObjectPredicates.inAuditObjectTypes;
import static ru.i_novus.ms.audit.repository.predicates.AuditPredicates.*;
import static ru.i_novus.ms.audit.repository.predicates.AuditPredicates.isEventDateBetween;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryService {

    private static Predicate getAuditEventPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (criteria.getEventDateFrom() != null && criteria.getEventDateTo() != null) {
            where.and(isEventDateBetween(criteria.getEventDateFrom(), criteria.getEventDateTo()));
        } else {
            if (criteria.getEventDateFrom() != null) {
                where.and(isEventDateGoe(criteria.getEventDateFrom()));
            }
            if (criteria.getEventDateTo() != null) {
                where.and(isEventDateLoe(criteria.getEventDateTo()));
            }
        }

        if (ArrayUtils.isNotEmpty(criteria.getAuditEventType())) {
            where.and(inEventTypeNames(criteria.getAuditEventType()));
        }

        return where.getValue();
    }

    private static Predicate getAuditObjectPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (ArrayUtils.isNotEmpty(criteria.getObjectType())) {
            where.and(inObjectTypeNames(criteria.getObjectType()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getObjectName())) {
            where.and(inObjectNameNames(criteria.getObjectName()));
        }

        if (criteria.getObjectId() != null) {
            where.and(isObjectIdEquals(criteria.getObjectId()));
        }

        return where.getValue();
    }

    private static Predicate getAuditPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (criteria.getId() != null) {
            where.and(isIdContains(criteria.getId()));
        }

        if (criteria.getUserId() != null) {
            where.and(isUserIdEquals(criteria.getUserId()));
        }

        if (criteria.getUsername() != null) {
            where.and(isUsernameEquals(criteria.getUsername()));
        }

        if (criteria.getSourceWorkstation() != null) {
            where.and(isSourceWorkstationContains(criteria.getSourceWorkstation()));
        }

        if (criteria.getContext() != null) {
            where.and(isContextContains(criteria.getContext()));
        }

        if (criteria.getHostname() != null) {
            where.and(isHostnameContains(criteria.getHostname()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getSourceApplication())) {
            where.and(inSourceApplicationNames(criteria.getSourceApplication()));
        }

        if (criteria.getAuditTypeId() != null) {
            where.and(isAuditTypeIdEquals(criteria.getAuditTypeId()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getSender())) {
            where.and(inSenders(criteria.getSender()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getReceiver())) {
            where.and(inReceivers(criteria.getReceiver()));
        }

        return where.getValue();
    }

    static Predicate toPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(getAuditEventPredicate(criteria))
                .and(getAuditObjectPredicate(criteria))
                .and(getAuditPredicate(criteria));

        return where.getValue() != null ? where.getValue() : Expressions.asBoolean(true).isTrue();
    }

    static Predicate toPredicate(AuditEventTypeCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (criteria.getAuditTypeId() != null) {
            where.and(EventTypePredicates.eqAuditTypeId(criteria.getAuditTypeId()));
        }
        if (criteria.getName() != null) {
            where.and(EventTypePredicates.containsName(criteria.getName()));
        }

        return where.getValue() != null ? where.getValue() : Expressions.asBoolean(true).isTrue();
    }

    static Predicate toPredicate(AuditSourceApplicationCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (criteria.getCode() != null) {
            where.and(QAuditSourceApplicationEntity.auditSourceApplicationEntity.code.containsIgnoreCase(criteria.getCode().trim()));
        }

        return where.getValue() != null ? where.getValue() : Expressions.asBoolean(true).isTrue();
    }

    static Predicate toPredicate(AuditObjectCriteria criteria, List<String> auditObjectTypes) {
        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotBlank(criteria.getTypeOrName())) {
            String str = criteria.getTypeOrName().trim();
            where
                .and(QAuditObjectEntity.auditObjectEntity.type.containsIgnoreCase(str))
                .or(QAuditObjectEntity.auditObjectEntity.name.containsIgnoreCase(str));
        }
        if (auditObjectTypes != null && !auditObjectTypes.isEmpty()) where.and(inAuditObjectTypes(auditObjectTypes));

        return where.getValue() != null ? where.getValue() : Expressions.asBoolean(true).isTrue();
    }

}
