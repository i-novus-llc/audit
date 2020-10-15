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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditEventTypeEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditEventTypeBuilder;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.repository.EventTypeRepository;

import java.util.Optional;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public Page<AuditEventType> search(AuditEventTypeCriteria criteria) {

        return eventTypeRepository.findAll(
                        QueryService.toPredicate(criteria),
                        PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getDefaultOrders()))
        ).map(AuditEventTypeBuilder::buildByEntity);
    }

    public void createIfNotPresent(String name, Short auditType) {
        Optional<AuditEventTypeEntity> eventTypeEntity = eventTypeRepository.findByNameAndAuditTypeId(name, auditType);

        if (eventTypeEntity.isEmpty()) {
            eventTypeRepository.save(
                    AuditEventTypeEntityBuilder.buildEntity(name, auditType)
            );
        }
    }
}
