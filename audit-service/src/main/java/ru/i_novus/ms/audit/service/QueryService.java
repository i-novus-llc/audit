package ru.i_novus.ms.audit.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.model.AuditCriteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

//        if (nonNull(criteria.getAuditObjectTypes())) {
//            List<UUID> ids = Arrays.stream(criteria.getAuditObjectTypes()).map(UUID::fromString).collect(Collectors.toList());
//            where.and(inObjectTypeIds(ids));
//        }
//
//        if (nonNull(criteria.getAuditObjectNames())) {
//            List<UUID> ids = Arrays.stream(criteria.getAuditObjectNames()).map(UUID::fromString).collect(Collectors.toList());
//            where.and(inObjectNameIds(ids));
//        }

        if (nonNull(criteria.getAuditObjectTypes())) {
            where.and(inObjectTypeNames(criteria.getAuditObjectTypes()));
        }

        if (nonNull(criteria.getAuditObjectNames())) {
            where.and(inObjectNameNames(criteria.getAuditObjectNames()));
        }

        if (nonNull(criteria.getObjectId()))
            where.and(isObjectIdEquals(criteria.getObjectId()));

        if (nonNull(criteria.getUserId()))
            where.and(isUserIdEquals(criteria.getUserId()));

        if (nonNull(criteria.getUsername()))
            where.and(isUsernameEquals(criteria.getUsername()));

        if (nonNull(criteria.getAuditSourceWorkstation()))
            where.and(isSourceWorkstationContains(criteria.getAuditSourceWorkstation()));

        if (nonNull(criteria.getContext()))
            where.and(isContextContains(criteria.getContext()));

        if (nonNull(criteria.getHostname()))
            where.and(isHostnameContains(criteria.getHostname()));

//        if(nonNull(criteria.getAuditSourceApplications())){
//            List<UUID> ids = Arrays.stream(criteria.getAuditSourceApplications()).map(UUID::fromString).collect(Collectors.toList());
//            where.and(inSourceApplicationIds(ids));
//        }

        if(nonNull(criteria.getAuditSourceApplications())){
            where.and(inSourceApplicationNames(criteria.getAuditSourceApplications()));
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
