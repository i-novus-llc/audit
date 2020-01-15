package ru.i_novus.ms.audit.service;

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

import java.time.LocalDateTime;
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

    @Autowired
    private EventTypeService eventTypeService;

    public Optional<AuditEntity> getById(UUID id) {
        Optional<AuditEntity> optional = auditRepository.searchEntityBylastMonth(id);
        return optional.isEmpty() ? auditRepository.findById(id) : optional;
    }


    public Page<Audit> search(AuditCriteria criteria) {
        return (searchEntity(criteria)).map(AuditBuilder::getAuditByEntity);
    }

    /**
     * получение последней записи из журнала
     * @param auditType Тип журнала
     * @param sourceApplication Идентификатор системы.
     * @return последняя запись из аудита если есть, иначе null
     */
    public Audit getLastAudit(Short auditType, String sourceApplication) {
        Optional<AuditEntity> auditEntity
                = auditRepository.findFirstByAuditTypeIdAndAuditSourceApplicationOrderByEventDateDesc(auditType, sourceApplication);

        return auditEntity.map(AuditBuilder::getAuditByEntity).orElse(null);
    }

    public boolean auditExists(Short auditTypeId, LocalDateTime eventDate, String eventType, String userId, String auditSourceApplication, String context) {
        return auditRepository.existsByAuditTypeIdAndEventDateAndEventTypeAndUserIdAndAuditSourceApplicationAndContext
                (auditTypeId, eventDate, eventType, userId, auditSourceApplication, context);
    }

    private Page<AuditEntity> searchEntity(AuditCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getOrdersOrDefault()));

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
        if (!StringUtils.isEmpty(request.getEventType()) && request.getAuditType() != null) {
            eventTypeService.createIfNotPresent(request.getEventType(), request.getAuditType());
        }

        return auditRepository.save(AuditEntityBuilder.buildEntity(request));
    }

}
