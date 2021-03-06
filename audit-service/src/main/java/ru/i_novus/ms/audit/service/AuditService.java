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

    @Autowired
    private AuditTypeService auditTypeService;

    public Optional<AuditEntity> getById(UUID id) {
        Optional<AuditEntity> optional = auditRepository.searchEntityByLastMonth(id);
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
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getDefaultOrders()));

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
        if (request.getAuditType() != null) {
            auditTypeService.createIfNotPresent(request.getAuditType());
        }

        return auditRepository.save(AuditEntityBuilder.buildEntity(request));
    }

}
