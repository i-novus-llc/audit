package ru.i_novus.ms.audit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.i_novus.ms.audit.service.ObjectNameService;
import ru.i_novus.ms.audit.service.ObjectTypeService;
import ru.i_novus.ms.audit.service.SourceApplicationService;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

import java.util.Collection;

@RestController
public class AuditReferenceRestImpl implements AuditReferenceRest {

    @Autowired
    private ObjectNameService objectNameService;

    @Autowired
    private ObjectTypeService objectTypeService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    public Collection getObjectNames() {
        return objectNameService.getAll();
    }

    public Collection getObjectTypes() {
        return objectTypeService.getAll();
    }

    public Collection getSourceApplications() {
        return sourceApplicationService.getAll();
    }

}
