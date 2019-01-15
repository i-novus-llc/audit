package ru.i_novus.ms.audit.client.impl.converter;

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
import ru.i_novus.ms.audit.model.AuditRequest;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class RequestConverter {

    private static final Logger logger = LoggerFactory.getLogger(RequestConverter.class);

    private UserAccessor userAccessor;

    private SourceApplicationAccessor applicationAccessor;

    private SourceWorkstationAccessor workstationAccessor;

    public RequestConverter(@Autowired UserAccessor userAccessor,
                            @Autowired(required = false) SourceApplicationAccessor applicationAccessor,
                            @Autowired(required = false) SourceWorkstationAccessor workstationAccessor) {
        this.userAccessor = userAccessor;
        this.applicationAccessor = applicationAccessor;
        this.workstationAccessor = workstationAccessor;
    }

    public AuditRequest toAuditRequest(AuditClientRequest request) {
        AuditRequest auditRequest = new AuditRequest();
        
        auditRequest.setEventDate(isNull(request.getEventDate()) ? LocalDateTime.now() : request.getEventDate());
        auditRequest.setEventType(request.getEventType());
        auditRequest.setObjectType(request.getObjectType());
        auditRequest.setObjectId(request.getObjectId());
        auditRequest.setObjectName(request.getObjectName());
        auditRequest.setContext(request.getContext());

        if (nonNull(applicationAccessor))
            auditRequest.setSourceApplication(applicationAccessor.get());

        if (nonNull(workstationAccessor))
            auditRequest.setSourceWorkstation(workstationAccessor.get());

        if (isNull(userAccessor)) {
            throw new AuditException("UserAccessor is undefined");
        }

        if (isNull(userAccessor.get())) {
            logger.error("User is undefined. Request: {}", request.toString());
            throw new AuditException("User is undefined");
        }
        
        User user = userAccessor.get();
        if (isNull(user.getUserId()) || user.getUserId().isBlank()) {
            logger.error("Invalid userId. User: {}. Request: {}", user, request.toString());
            throw new AuditException("Invalid userId (null or blank)");
        }

        if (isNull(user.getUsername()) || user.getUsername().isBlank()) {
            logger.error("Invalid username. User: {}. Request: {}", user, request.toString());
            throw new AuditException("Invalid username (null or blank)");
        }

        auditRequest.setUserId(user.getUserId());
        auditRequest.setUsername(user.getUsername());

        return auditRequest;
    }
}
