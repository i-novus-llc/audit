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
import ru.i_novus.ms.audit.builder.entity.AuditObjectEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditObjectBuilder;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.repository.AuditObjectRepository;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class AuditObjectService {

    @Autowired
    private AuditObjectRepository auditObjectRepository;
    @Autowired
    private AuditRepository auditRepository;

    public Page<AuditObject> search(AuditObjectCriteria criteria) {
        return auditObjectRepository.findAll(
                QueryService.toPredicate(criteria, getAuditObjectTypes(criteria.getAuditTypeId())),
                PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), criteria.getSort())
        ).map(AuditObjectBuilder::buildByEntity);
    }

    public void createIfNotPresent(String name, String type) {
        Optional<AuditObjectEntity> auditObjectEntity = auditObjectRepository.findByNameAndType(name, type);

        if (auditObjectEntity.isEmpty()) {
            auditObjectRepository.save(AuditObjectEntityBuilder.buildEntity(name, type));
        }
    }

    /**
     * Фильтруем типы объектов, характерные только для данного типа журнала
     *
     * @param auditTypeId Идентификатор типа журнала.
     * @return Все варианты типов объектов, характерные только для данного типа журнала
     */
    private String[] getAuditObjectTypes(Short auditTypeId) {
        AuditCriteria auditCriteria = new AuditCriteria();
        auditCriteria.setAuditTypeId(auditTypeId);
        return StreamSupport.stream(auditRepository.findAll(QueryService.toPredicate(auditCriteria)).spliterator(), false)
                .map(AuditEntity::getAuditObjectType).filter(Objects::nonNull).distinct().toArray(String[]::new);
    }
}
