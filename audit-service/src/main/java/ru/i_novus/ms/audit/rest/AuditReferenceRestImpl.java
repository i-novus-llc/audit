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
