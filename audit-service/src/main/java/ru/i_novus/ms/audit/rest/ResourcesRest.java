package ru.i_novus.ms.audit.rest;

import net.n2oapp.criteria.api.CollectionPage;
import net.n2oapp.criteria.api.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.i_novus.ms.audit.entity.AuditObjectName;
import ru.i_novus.ms.audit.entity.AuditObjectType;
import ru.i_novus.ms.audit.entity.AuditSourceApplication;
import ru.i_novus.ms.audit.service.ObjectNameService;
import ru.i_novus.ms.audit.service.ObjectTypeService;
import ru.i_novus.ms.audit.service.SourceApplicationService;

import java.util.Collection;
import java.util.UUID;

@RestController
public class ResourcesRest {

    @Autowired
    private ObjectNameService objectNameService;

    @Autowired
    private ObjectTypeService objectTypeService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    @GetMapping("/objectName")
    public CollectionPage getObjectNames() {
        Collection<AuditObjectName> objectNames =  objectNameService.getAll();
        return new CollectionPage<>(objectNames.size(), objectNames, new Criteria());
    }

    @GetMapping("/objectName/{id}")
    public AuditObjectName getObjectName(@PathVariable String id) {
        return objectNameService.getById(UUID.fromString(id));
    }

    @GetMapping("/objectType")
    public CollectionPage getObjectTypes() {
        Collection<AuditObjectType> objectTypes =  objectTypeService.getAll();
        return new CollectionPage<>(objectTypes.size(), objectTypes, new Criteria());
    }

    @GetMapping("/objectType/{id}")
    public AuditObjectType getObjectType(@PathVariable String id) {
        return objectTypeService.getById(UUID.fromString(id));
    }

    @GetMapping("/sourceApplication")
    public CollectionPage getSourceApplications() {
        Collection<AuditSourceApplication> auditSourceApplications =  sourceApplicationService.getAll();
        return new CollectionPage<>(auditSourceApplications.size(), auditSourceApplications, new Criteria());
    }

    @GetMapping("/sourceApplication/{id}")
    public AuditSourceApplication getSourceApplication(@PathVariable String id) {
        return sourceApplicationService.getById(UUID.fromString(id));
    }
}
