package ru.i_novus.ms.audit.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.model.AuditCriteria;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;
import static ru.i_novus.ms.audit.repository.AuditPredicates.*;

public class QueryService {

    public static Predicate toPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(criteria.getEventDateFrom()))
            where.and(isEventDateAfterOrEquals(criteria.getEventDateFrom()));

        if (nonNull(criteria.getEventDateTo()))
            where.and(isEventDateBeforeOrEquals(criteria.getEventDateTo()));

        if (nonNull(criteria.getEventType()))
            where.and(isEventTypeEquals(criteria.getEventType()));

        if (nonNull(criteria.getObjectType()) && criteria.getObjectType().length > 0) {
            where.and(inObjectTypeNames(criteria.getObjectType()));
        }

        if (nonNull(criteria.getObjectName()) && criteria.getObjectName().length > 0) {
            where.and(inObjectNameNames(criteria.getObjectName()));
        }

        if (nonNull(criteria.getObjectId()))
            where.and(isObjectIdEquals(criteria.getObjectId()));

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

        if (nonNull(criteria.getSourceApplication()) && criteria.getSourceApplication().length > 0) {
            where.and(inSourceApplicationNames(criteria.getSourceApplication()));
        }

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


}
