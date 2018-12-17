package ru.i_novus.ms.audit.rest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.exception.NotFoundException;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditRequest;
import ru.i_novus.ms.audit.repository.AuditRepository;
import ru.i_novus.ms.audit.service.api.AuditService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.i_novus.ms.audit.repository.AuditPredicates.*;

@Controller
public class AuditRest implements AuditService {

    @Autowired
    private AuditRepository repository;

    @Override
    @Transactional
    public Audit getById(UUID id) {
        Optional<AuditEntity> auditEntity = repository.findById(id);
        if (auditEntity.isEmpty())
            throw new NotFoundException();
        return model(auditEntity.get());
    }

    @Override
    @Transactional
    public Page<Audit> search(AuditCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(), toSort(criteria));
        Page<AuditEntity> page = repository.findAll(toPredicate(criteria), pageable);
        return page.map(this::model);
    }

    @Override
    @Transactional
    public Audit add(AuditRequest request) {
        if (isNull(request))
            throw new IllegalArgumentException();
        return model(repository.save(entity(request)));
    }

    private static Predicate toPredicate(AuditCriteria criteria) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(criteria.getEventDateFrom()))
            where.and(isEventDateAfterOrEquals(criteria.getEventDateFrom()));

        if (nonNull(criteria.getEventDateTo()))
            where.and(isEventDateBeforeOrEquals(criteria.getEventDateTo()));

        if (nonNull(criteria.getEventType()))
            where.and(isEventTypeEquals(criteria.getEventType()));

        if (nonNull(criteria.getObjectType()))
            where.and(isObjectTypeEquals(criteria.getObjectType()));

        if (nonNull(criteria.getObjectName()))
            where.and(isObjectNameEquals(criteria.getObjectName()));

        if (nonNull(criteria.getObjectId()))
            where.and(isObjectIdEquals(criteria.getObjectId()));

        if (nonNull(criteria.getUserId()))
            where.and(isUserIdEquals(criteria.getUserId()));

        if (nonNull(criteria.getUsername()))
            where.and(isUsernameEquals(criteria.getUsername()));

        if (nonNull(criteria.getSourceApplication()))
            where.and(isSourceApplicationContains(criteria.getSourceApplication()));

        if (nonNull(criteria.getSourceWorkstation()))
            where.and(isSourceWorkstationContains(criteria.getSourceWorkstation()));

        if (nonNull(criteria.getContext()))
            where.and(isContextContains(criteria.getContext()));

        return where.getValue();
    }

    private Sort toSort(AuditCriteria criteria) {
        List<Sort.Order> orders = criteria.getOrders();
        if (CollectionUtils.isEmpty(orders))
            orders = Collections.singletonList(new Sort.Order(Sort.Direction.DESC, "eventDate"));

        Sort sort = null;
        for (Sort.Order order : orders)
            if (sort == null) sort = sort(order);
            else sort.and(sort(order));
        return sort;
    }

    private static Sort sort(Sort.Order order) {
        return new Sort(order.getDirection(), order.getProperty());
    }

    private Audit model(AuditEntity entity) {
        if (isNull(entity)) return null;
        Audit model = new Audit();
        model.setId(entity.getId());
        model.setEventDate(entity.getEventDate());
        model.setEventType(entity.getEventType());
        model.setObjectType(entity.getObjectType());
        model.setObjectId(entity.getObjectId());
        model.setObjectName(entity.getObjectName());
        model.setUserId(entity.getUserId());
        model.setUsername(entity.getUsername());
        model.setSourceApplication(entity.getSourceApplication());
        model.setSourceWorkstation(entity.getSourceWorkstation());
        model.setContext(entity.getContext());
        model.setCreationDate(entity.getCreationDate());

        return model;
    }

    private static AuditEntity entity(AuditRequest request) {
        AuditEntity entity = new AuditEntity();
        entity.setEventDate(request.getEventDate());
        entity.setEventType(request.getEventType());
        entity.setObjectType(request.getObjectType());
        entity.setObjectId(request.getObjectId());
        entity.setObjectName(request.getObjectName());
        entity.setUserId(request.getUserId());
        entity.setUsername(request.getUsername());
        entity.setSourceApplication(request.getSourceApplication());
        entity.setSourceWorkstation(request.getSourceWorkstation());
        entity.setContext(request.getContext());

        return entity;
    }
}