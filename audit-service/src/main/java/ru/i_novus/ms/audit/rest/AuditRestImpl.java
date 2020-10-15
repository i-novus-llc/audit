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

package ru.i_novus.ms.audit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import ru.i_novus.ms.audit.builder.model.AuditBuilder;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.exception.NotFoundException;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.AbstractSsoEventsService;
import ru.i_novus.ms.audit.service.AuditService;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Controller
public class AuditRestImpl implements AuditRest {

    @Autowired
    private AuditService auditService;

    @Autowired
    private AbstractSsoEventsService ssoEventsService;

    @Override
    public Audit getById(UUID id) {
        Optional<AuditEntity> auditEntity = auditService.getById(id);
        return AuditBuilder.getAuditByEntity(auditEntity.orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<Audit> search(AuditCriteria criteria) {
        return auditService.search(criteria);
    }

    @Override
    public Audit add(AuditForm auditForm) {
        if (isNull(auditForm))
            throw new IllegalArgumentException();
        return AuditBuilder.getAuditByEntity(auditService.create(auditForm));
    }

    @Override
    public void startEventsSynchronize() {
        ssoEventsService.startSynchronization();
    }
}