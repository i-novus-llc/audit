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
import org.springframework.web.bind.annotation.RestController;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.model.AuditSourceApplication;
import ru.i_novus.ms.audit.model.AuditType;
import ru.i_novus.ms.audit.service.AuditObjectService;
import ru.i_novus.ms.audit.service.AuditTypeService;
import ru.i_novus.ms.audit.service.EventTypeService;
import ru.i_novus.ms.audit.service.SourceApplicationService;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

import java.util.Collection;

@RestController
public class AuditReferenceRestImpl implements AuditReferenceRest {

    @Autowired
    private AuditObjectService auditObjectService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private AuditTypeService auditTypeService;

    @Override
    public Page<AuditObject> getObjects(AuditObjectCriteria criteria) {
        return auditObjectService.search(criteria);
    }

    @Override
    public Page<AuditEventType> getEventType(AuditEventTypeCriteria criteria) {
        return eventTypeService.search(criteria);
    }

    @Override
    public Page<AuditSourceApplication> getSourceApplications(AuditSourceApplicationCriteria criteria) {
        return sourceApplicationService.search(criteria);
    }

    @Override
    public Collection<AuditType> getAuditTypes() {
        return auditTypeService.getDefaultAuditTypes();
    }
}
