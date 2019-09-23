package ru.i_novus.ms.audit.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.i_novus.ms.audit.builder.entity.AuditEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditBuilder;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AuditObjectService auditObjectService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    public Optional<AuditEntity> getById(UUID id) {
        Optional<AuditEntity> optional = auditRepository.searchEntityBylastMonth(id);
        return optional.isEmpty() ? auditRepository.findById(id) : optional;
    }


    public Page<Audit> search(AuditCriteria criteria) {
        if (criteria.getSortingColumn() != null) {
            Sort.Order order = new Sort.Order(
                    Sort.Direction.fromString(criteria.getSortingOrder() == null ? "DESC" : criteria.getSortingOrder()),
                    criteria.getSortingColumn()
            );
            criteria.setOrders(Lists.newArrayList(order));
        }

        return (searchEntity(criteria)).map(AuditBuilder::getAuditByEntity);
    }

    private Page<AuditEntity> searchEntity(AuditCriteria criteria) {
        int size = criteria.getPageSize() > 0 ? criteria.getPageSize() : 10;
        int pageNum = criteria.getPageNumber() > 0 ? criteria.getPageNumber() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNum, size, QueryService.toSort(criteria));

        return auditRepository.findAll(QueryService.toPredicate(criteria), pageable);
    }

    public AuditEntity create(AuditForm request) {
        if (!StringUtils.isEmpty(request.getObjectType())) {
            auditObjectService.createIfNotPresent(request.getObjectName(), request.getObjectType());
        }
        if (!StringUtils.isEmpty(request.getSourceApplication())) {
            sourceApplicationService.createIfNotPresent(request.getSourceApplication());
        }
        if (!StringUtils.isEmpty(request.getSender())) {
            sourceApplicationService.createIfNotPresent(request.getSender());
        }
        if (!StringUtils.isEmpty(request.getReceiver())) {
            sourceApplicationService.createIfNotPresent(request.getReceiver());
        }

        return auditRepository.save(AuditEntityBuilder.buildEntity(request));
    }

}
