package ru.i_novus.ms.audit.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.entity.QAuditObjectEntity;
import ru.i_novus.ms.audit.entity.QAuditSourceApplicationEntity;
import ru.i_novus.ms.audit.repository.predicates.EventTypePredicates;

import java.util.Collections;
import java.util.List;

import static ru.i_novus.ms.audit.repository.predicates.AuditPredicates.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryService {

    private static Predicate getAuditEventPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (criteria.getEventDateFrom() != null && criteria.getEventDateTo() != null) {
            where.and(isEventDateBetween(criteria.getEventDateFrom(), criteria.getEventDateTo()));
        } else {
            if (criteria.getEventDateFrom() != null) {
                where.and(isEventDateAfterOrEquals(criteria.getEventDateFrom()));
            }
            if (criteria.getEventDateTo() != null) {
                where.and(isEventDateBeforeOrEquals(criteria.getEventDateTo()));
            }
        }
        if (criteria.getEventType() != null) {
            where.and(isEventTypeEquals(criteria.getEventType()));
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

        if (ArrayUtils.isNotEmpty(criteria.getAuditTypeId())) {
            where.and(inAuditTypeIds(criteria.getAuditTypeId()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getSender())) {
            where.and(inSenders(criteria.getSender()));
        }

        if (ArrayUtils.isNotEmpty(criteria.getReceiver())) {
            where.and(inRecivers(criteria.getReceiver()));
        }

        return where.getValue();
    }

    public static Predicate toPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(getAuditEventPredicate(criteria))
                .and(getAuditObjectPredicate(criteria))
                .and(getAuditPredicate(criteria));

        return where.getValue();
    }

    public static Sort toSort(AuditCriteria criteria) {
        List<Sort.Order> orders = criteria.getOrders();
        if (CollectionUtils.isEmpty(orders)) {
            orders = Collections.singletonList(new Sort.Order(Sort.Direction.DESC, "eventDate"));
        }
        return Sort.by(orders);
    }

    public static Sort sort(Sort.Order order) {
        return new Sort(order.getDirection(), order.getProperty());
    }

    static Predicate toPredicate(AuditEventTypeCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (criteria.getAuditTypeId() != null) {
            where.and(EventTypePredicates.eqAuditTypeId(criteria.getAuditTypeId()));
        }
        if (criteria.getName() != null) {
            where.and(EventTypePredicates.containsName(criteria.getName()));
        }

        return where.getValue();
    }

    static Predicate toPredicate(AuditSourceApplicationCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (criteria.getCode() != null) {
            where.and(QAuditSourceApplicationEntity.auditSourceApplicationEntity.code.containsIgnoreCase(criteria.getCode().trim()));
        }
        return where.getValue();
    }

    static Predicate toPredicate(AuditObjectCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (criteria.getName() != null) {
            where.and(QAuditObjectEntity.auditObjectEntity.name.containsIgnoreCase(criteria.getName().trim()));
        }

        return where.getValue();
    }

}
