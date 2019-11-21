package ru.i_novus.ms.audit.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

import java.util.List;

/**
 * Контроллер ресурса аудита событий
 */
public class AuditReferenceFrontendController {
    private AuditReferenceRest auditReferenceRest;

    public AuditReferenceFrontendController(AuditReferenceRest auditReferenceRest) {
        this.auditReferenceRest = auditReferenceRest;
    }

    public Page<AuditObject> findAllObjects(AuditObjectCriteria criteria) {
        criteria.setOrders(List.of(
            new Sort.Order(Sort.Direction.ASC, "type"),
            new Sort.Order(Sort.Direction.ASC, "name"),
            new Sort.Order(Sort.Direction.ASC, "id"))
        );
        Page<AuditObject> result = auditReferenceRest.getObjects(criteria);
        result.getContent().forEach(x -> x.setName(String.join(" ", x.getType(), ObjectUtils.defaultIfNull(x.getName(), ""))));
        return result;
    }
}
