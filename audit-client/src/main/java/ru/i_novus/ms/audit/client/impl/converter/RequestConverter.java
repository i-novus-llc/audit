package ru.i_novus.ms.audit.client.impl.converter;

import lombok.AccessLevel;
import lombok.Setter;
import net.n2oapp.platform.i18n.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

@Component
@Setter(AccessLevel.PROTECTED)
public class RequestConverter {

    private static final Logger logger = LoggerFactory.getLogger(RequestConverter.class);

    @Autowired(required = false)
    private SourceApplicationAccessor applicationAccessor;

    @Autowired
    private Messages messages;

    private void assertRequiredFields(AuditClientRequest request) {
        if (request.getEventType() == null || request.getEventType().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidEventType"));

        if (request.getUserId() == null || request.getUserId().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidUserId"));

        if (request.getUsername() == null || request.getUsername().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidUsername"));

        if (request.getContext() == null || request.getContext().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidContext"));

        if (request.getAuditType() == null)
            throw new AuditException(messages.getMessage("requestConverter.invalidAuditType"));
    }

    public AuditForm toAuditRequest(AuditClientRequest request) {
        assertRequiredFields(request);

        AuditForm auditForm = new AuditForm();

        auditForm.setEventDate(request.getEventDate() == null ? LocalDateTime.now() : request.getEventDate());
        auditForm.setEventType(request.getEventType());
        auditForm.setObjectType(request.getObjectType());
        auditForm.setObjectId(request.getObjectId());
        auditForm.setObjectName(request.getObjectName());
        auditForm.setUserId(request.getUserId());
        auditForm.setUsername(request.getUsername());
        auditForm.setSourceWorkstation(request.getSourceWorkstation());
        auditForm.setContext(request.getContext());
        auditForm.setHostname(request.getHostname());
        auditForm.setAuditType(request.getAuditType());
        auditForm.setSender(request.getSender());
        auditForm.setReceiver(request.getReceiver());

        if (request.getSourceApplication() != null) {
            auditForm.setSourceApplication(request.getSourceApplication());
        } else if (applicationAccessor != null) {
            auditForm.setSourceApplication(applicationAccessor.get());
        }

        return auditForm;
    }
}
