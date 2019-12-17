package ru.i_novus.ms.audit.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.io.IOException;
import java.util.UUID;

/**
 * Контроллер данных аудита для страниц n2o
 */
public class AuditN2oController {
    private AuditRest auditRest;

    public AuditN2oController(AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    public Audit getAudit(UUID id) {
        Audit audit = auditRest.getById(id);
        ObjectMapper mapper = new ObjectMapper();
        String ctx;
        try {
            JsonNode json = mapper.readTree(audit.getContext());
            ctx = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            ctx = audit.getContext();
        }
        audit.setContext(ctx);
        return audit;
    }
}
