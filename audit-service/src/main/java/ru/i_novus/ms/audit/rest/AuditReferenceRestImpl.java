package ru.i_novus.ms.audit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.i_novus.ms.audit.model.EventTypeCriteria;
import ru.i_novus.ms.audit.service.AuditObjectService;
import ru.i_novus.ms.audit.service.AuditTypeService;
import ru.i_novus.ms.audit.service.EventTypeService;
import ru.i_novus.ms.audit.service.SourceApplicationService;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

import java.util.ArrayList;
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

    public Collection getObjects() {
        return auditObjectService.getAll();
    }

    public Collection getEventType(EventTypeCriteria eventTypeCriteria) {
        return new ArrayList<>(eventTypeService.search(eventTypeCriteria));
    }

    public Collection getSourceApplications() {
        return sourceApplicationService.getAll();
    }

    public Collection getAuditTypes() {
        return auditTypeService.getAll();
    }
}
