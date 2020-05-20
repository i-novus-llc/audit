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
import ru.i_novus.ms.audit.service.AuditService;
import ru.i_novus.ms.audit.service.AbstractSsoEventsService;
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