package ru.i_novus.ms.audit.client.impl.converter;

import lombok.AccessLevel;
import lombok.Setter;
import net.n2oapp.platform.i18n.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.SourceWorkstationAccessor;
import ru.i_novus.ms.audit.client.UserAccessor;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

@Component
@Setter(AccessLevel.PROTECTED)
public class RequestConverter {

    private static final Logger logger = LoggerFactory.getLogger(RequestConverter.class);

    @Autowired
    private UserAccessor userAccessor;

    @Autowired(required = false)
    private SourceApplicationAccessor applicationAccessor;

    @Autowired(required = false)
    private SourceWorkstationAccessor workstationAccessor;

    @Autowired
    private Messages messages;

    private void assertUserAccessor(AuditClientRequest request) {
        if (userAccessor == null) {
            throw new AuditException(messages.getMessage("requestConverter.undefinedUserAccessor"));
        }

        if (userAccessor.get() == null) {
            logger.error("User is undefined. Request: {}", request);
            throw new AuditException(messages.getMessage("requestConverter.undefinedUser"));
        }

        User user = userAccessor.get();
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            logger.error("Invalid userId. User: {}. Request: {}", user, request);
            throw new AuditException(messages.getMessage("requestConverter.invalidUserId"));
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            logger.error("Invalid username. User: {}. Request: {}", user, request);
            throw new AuditException(messages.getMessage("requestConverter.invalidUsername"));
        }
    }

    private void assertRequiredFields(AuditClientRequest request) {
        if (request.getEventType() == null || request.getEventType().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidEventType"));

        if (request.getObjectType() == null || request.getObjectType().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidObjectType"));

        if (request.getObjectId() == null || request.getObjectId().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidObjectId"));

        if (request.getObjectName() == null || request.getObjectName().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidObjectName"));

        if (request.getContext() == null || request.getContext().isBlank())
            throw new AuditException(messages.getMessage("requestConverter.invalidContext"));

        if (request.getAuditTypeId() == null)
            throw new AuditException(messages.getMessage("requestConverter.invalidAuditTypeId"));
    }

    public AuditForm toAuditRequest(AuditClientRequest request) {
        assertUserAccessor(request);
        assertRequiredFields(request);

        AuditForm auditForm = new AuditForm();

        auditForm.setEventDate(request.getEventDate() == null ? LocalDateTime.now() : request.getEventDate());
        auditForm.setEventType(request.getEventType());
        auditForm.setObjectType(request.getObjectType());
        auditForm.setObjectId(request.getObjectId());
        auditForm.setObjectName(request.getObjectName());
        auditForm.setContext(request.getContext());
        auditForm.setHostname(request.getHostname());
        auditForm.setAuditTypeId(request.getAuditTypeId());
        auditForm.setUserId(userAccessor.get().getUserId());
        auditForm.setUsername(userAccessor.get().getUsername());

        if (applicationAccessor != null)
            auditForm.setSourceApplication(applicationAccessor.get());

        if (workstationAccessor != null)
            auditForm.setSourceWorkstation(workstationAccessor.get());

        return auditForm;
    }
}
