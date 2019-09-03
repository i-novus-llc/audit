package ru.i_novus.ms.audit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.i_novus.ms.audit.service.AuditObjectService;
import ru.i_novus.ms.audit.service.SourceApplicationService;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

import java.util.Collection;

@RestController
public class AuditReferenceRestImpl implements AuditReferenceRest {

    @Autowired
    private AuditObjectService auditObjectService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    public Collection getObjects() {
        return auditObjectService.getAll();
    }

    public Collection getSourceApplications() {
        return sourceApplicationService.getAll();
    }

}
