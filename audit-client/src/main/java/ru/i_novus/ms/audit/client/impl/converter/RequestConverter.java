package ru.i_novus.ms.audit.client.impl.converter;

import lombok.AccessLevel;
import lombok.Setter;
import net.n2oapp.platform.i18n.Messages;
import net.n2oapp.platform.i18n.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.SourceWorkstationAccessor;
import ru.i_novus.ms.audit.client.UserAccessor;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@Setter(AccessLevel.PROTECTED)
public class RequestConverter {

    @Autowired(required = false)
    private UserAccessor userAccessor;

    @Autowired(required = false)
    private SourceWorkstationAccessor workstationAccessor;

    @Autowired(required = false)
    private SourceApplicationAccessor applicationAccessor;

    @Autowired
    private Messages messages;

    public AuditForm toAuditRequest(AuditClientRequest request) {
        assertRequiredFields(request);

        AuditForm auditForm = convertAccessorFields(request);

        auditForm.setEventDate(request.getEventDate() == null ? LocalDateTime.now(Clock.systemUTC()) : request.getEventDate());
        auditForm.setContext(request.getContext());
        auditForm.setAuditType(request.getAuditType());

        if (request.getEventType() != null)
            auditForm.setEventType(getMessage(request.getEventType().getValue(), request.getEventType().getArgs()));
        if (request.getObjectType() != null)
            auditForm.setObjectType(getMessage(request.getObjectType().getValue(), request.getObjectType().getArgs()));
        if (request.getObjectId() != null)
            auditForm.setObjectId(getMessage(request.getObjectId().getValue(), request.getObjectId().getArgs()));
        if (request.getObjectName() != null)
            auditForm.setObjectName(getMessage(request.getObjectName().getValue(), request.getObjectName().getArgs()));
        if (request.getHostname() != null)
            auditForm.setHostname(getMessage(request.getHostname().getValue(), request.getHostname().getArgs()));
        if (request.getSender() != null)
            auditForm.setSender(getMessage(request.getSender().getValue(), request.getSender().getArgs()));
        if (request.getReceiver() != null)
            auditForm.setReceiver(getMessage(request.getReceiver().getValue(), request.getReceiver().getArgs()));

        return auditForm;
    }

    private void assertRequiredFields(AuditClientRequest request) {
        if (request.getEventType() == null || StringUtils.isBlank(request.getEventType().getValue()))
            throw new UserException("audit.clientException.notSetEventType");

        if (StringUtils.isBlank(request.getContext()))
            throw new UserException("audit.clientException.notSetContext");

        if (request.getAuditType() == null)
            throw new UserException("audit.clientException.notSetAuditType");
    }

    private AuditForm convertAccessorFields(AuditClientRequest request) {
        AuditForm auditForm = new AuditForm();

        if (request.getUserId() != null && request.getUserId().getValue() != null) {
            auditForm.setUserId(getMessage(request.getUserId().getValue(), request.getUserId().getArgs()));
        } else if (userAccessor != null && userAccessor.get() != null && userAccessor.get().getUserId() != null) {
            auditForm.setUserId(userAccessor.get().getUserId());
        }

        if (request.getUsername() != null && request.getUsername().getValue() != null) {
            auditForm.setUsername(getMessage(request.getUsername().getValue(), request.getUsername().getArgs()));
        } else if (userAccessor != null && userAccessor.get() != null && userAccessor.get().getUsername() != null) {
            auditForm.setUsername(userAccessor.get().getUsername());
        }

        if (request.getSourceApplication() != null && request.getSourceApplication().getValue() != null) {
            auditForm.setSourceApplication(getMessage(request.getSourceApplication().getValue(), request.getSourceApplication().getArgs()));
        } else if (applicationAccessor != null) {
            auditForm.setSourceApplication(applicationAccessor.get());
        }

        if (request.getSourceWorkstation() != null && request.getSourceWorkstation().getValue() != null) {
            auditForm.setSourceWorkstation(getMessage(request.getSourceWorkstation().getValue(), request.getSourceWorkstation().getArgs()));
        } else if (workstationAccessor != null) {
            auditForm.setSourceWorkstation(workstationAccessor.get());
        }

        return auditForm;
    }

    /**
     * Вспомогательный метод для обхода NPE при получении значения из messages
     *
     * @param code Код сообщения
     * @param args Параметры сообщения
     * @return Локализованное сообщение или null, если code был равен null
     */
    private String getMessage(String code, Object[] args) {
        if (code != null) {
            return messages.getMessage(code, args);
        } else {
            return null;
        }
    }
}
