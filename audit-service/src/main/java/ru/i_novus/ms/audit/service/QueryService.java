package ru.i_novus.ms.audit.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
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

import static java.util.Objects.nonNull;
import static ru.i_novus.ms.audit.repository.predicates.AuditPredicates.*;

public class QueryService {
    private QueryService() {
    }

    private static Predicate getAuditEventPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(criteria.getEventDateFrom()))
            where.and(isEventDateAfterOrEquals(criteria.getEventDateFrom()));

        if (nonNull(criteria.getEventDateTo()))
            where.and(isEventDateBeforeOrEquals(criteria.getEventDateTo()));

        if (nonNull(criteria.getEventType()))
            where.and(isEventTypeEquals(criteria.getEventType()));

        return where.getValue();
    }

    private static Predicate getAuditObjectPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(criteria.getObjectType()) && criteria.getObjectType().length > 0)
            where.and(inObjectNameNames(criteria.getObjectType()));

        if (nonNull(criteria.getObjectId()))
            where.and(isObjectIdEquals(criteria.getObjectId()));

        return where.getValue();
    }

    private static Predicate getAuditPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(criteria.getUserId()))
            where.and(isUserIdEquals(criteria.getUserId()));

        if (nonNull(criteria.getUsername()))
            where.and(isUsernameEquals(criteria.getUsername()));

        if (nonNull(criteria.getSourceWorkstation()))
            where.and(isSourceWorkstationContains(criteria.getSourceWorkstation()));

        if (nonNull(criteria.getContext()))
            where.and(isContextContains(criteria.getContext()));

        if (nonNull(criteria.getHostname()))
            where.and(isHostnameContains(criteria.getHostname()));

        if (nonNull(criteria.getSourceApplication()) && criteria.getSourceApplication().length > 0)
            where.and(inSourceApplicationNames(criteria.getSourceApplication()));

        if (nonNull(criteria.getAuditTypeId()) && criteria.getAuditTypeId().length > 0)
            where.and(inAuditTypeIds(criteria.getAuditTypeId()));

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
        if (CollectionUtils.isEmpty(orders))
            orders = Collections.singletonList(new Sort.Order(Sort.Direction.DESC, "eventDate"));
        return Sort.by(orders);
    }

    public static Sort sort(Sort.Order order) {
        return new Sort(order.getDirection(), order.getProperty());
    }

    static Predicate toPredicate(AuditEventTypeCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (nonNull(criteria.getAuditTypeId())) {
            where.and(EventTypePredicates.eqAuditTypeId(criteria.getAuditTypeId()));
        }
        if (nonNull(criteria.getName())) {
            where.and(EventTypePredicates.containsName(criteria.getName()));
        }

        return where.getValue();
    }

    static Predicate toPredicate(AuditSourceApplicationCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (nonNull(criteria.getCode())) {
            where.and(QAuditSourceApplicationEntity.auditSourceApplicationEntity.code.containsIgnoreCase(criteria.getCode().trim()));
        }
        return where.getValue();
    }

    static Predicate toPredicate(AuditObjectCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();
        if (nonNull(criteria.getName())) {
            where.and(QAuditObjectEntity.auditObjectEntity.name.containsIgnoreCase(criteria.getName().trim()));
        }

        return where.getValue();
    }

}
