package ru.i_novus.ms.audit.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.entity.AuditObjectNameEntity;
import ru.i_novus.ms.audit.entity.AuditObjectTypeEntity;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ObjectNameService objectNameService;

    @Autowired
    private ObjectTypeService objectTypeService;

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
        return (searchEntity(criteria)).map(AuditService::getAuditByEntity);
    }

    private Page<AuditEntity> searchEntity(AuditCriteria criteria) {
        int size = criteria.getPageSize() > 0 ? criteria.getPageSize() : 10;
        int pageNum = criteria.getPageNumber() > 0 ? criteria.getPageNumber() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNum, size, QueryService.toSort(criteria));
        return auditRepository.findAll(QueryService.toPredicate(criteria), pageable);
    }

    public AuditEntity create(AuditForm request) {

        AuditObjectNameEntity auditObjectName = objectNameService.getOrCreate(request.getObjectName());
        AuditObjectTypeEntity auditObjectType = objectTypeService.getOrCreate(request.getObjectType());
        AuditSourceApplicationEntity auditSourceApplication = sourceApplicationService.getOrCreate(request.getSourceApplication());

        AuditEntity entity = AuditEntity.builder()
                .auditObjectName(auditObjectName)
                .auditObjectType(auditObjectType)
                .auditSourceApplication(auditSourceApplication)
                .context(request.getContext())
                .eventDate(request.getEventDate())
                .eventType(request.getEventType())
                .userId(request.getUserId())
                .username(request.getUsername())
                .objectId(request.getObjectId())
                .sourceWorkstation(request.getSourceWorkstation())
                .hostname(request.getHostname())
                .build();
        return auditRepository.save(entity);
    }


    public static Audit getAuditByEntity(AuditEntity entity) {
        return Audit.builder()
                .creationDate(entity.getCreationDate())
                .id(entity.getId())
                .context(entity.getContext())
                .eventDate(entity.getEventDate())
                .eventType(entity.getEventType())
                .objectId(entity.getObjectId())
                .objectName(entity.getAuditObjectName().getName())
                .objectType(entity.getAuditObjectType().getName())
                .sourceApplication(entity.getAuditSourceApplication().getName())
                .sourceWorkstation(entity.getSourceWorkstation())
                .userId(entity.getUserId())
                .hostname(entity.getHostname())
                .username(entity.getUsername())
                .build();
    }
}
