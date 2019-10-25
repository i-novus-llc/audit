package ru.i_novus.ms.audit.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.n2oapp.platform.i18n.UserException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Getter
public class AuditClientRequest {

    @Setter
    private LocalDateTime eventDate;

    @Setter
    private Short auditType;

    private String context;
    private AuditClientRequestParam eventType;
    private AuditClientRequestParam objectType;
    private AuditClientRequestParam objectId;
    private AuditClientRequestParam objectName;
    private AuditClientRequestParam userId;
    private AuditClientRequestParam username;
    private AuditClientRequestParam sourceWorkstation;
    private AuditClientRequestParam sourceApplication;
    private AuditClientRequestParam hostname;
    private AuditClientRequestParam sender;
    private AuditClientRequestParam receiver;

    public void setEventType(String eventType, Object... args) {
        this.eventType = new AuditClientRequestParam(eventType, args);
    }

    public void setObjectType(String objectType, Object... args) {
        this.objectType = new AuditClientRequestParam(objectType, args);
    }

    public void setObjectId(String objectId, Object... args) {
        this.objectId = new AuditClientRequestParam(objectId, args);
    }

    public void setObjectName(String objectName, Object... args) {
        this.objectName = new AuditClientRequestParam(objectName, args);
    }

    public void setUserId(String userId, Object... args) {
        this.userId = new AuditClientRequestParam(userId, args);
    }

    public void setUsername(String username, Object... args) {
        this.username = new AuditClientRequestParam(username, args);
    }

    public void setSourceWorkstation(String sourceWorkstation, Object... args) {
        this.sourceWorkstation = new AuditClientRequestParam(sourceWorkstation, args);
    }

    public void setSourceApplication(String sourceApplication, Object... args) {
        this.sourceApplication = new AuditClientRequestParam(sourceApplication, args);
    }

    public void setHostname(String hostname, Object... args) {
        this.hostname = new AuditClientRequestParam(hostname, args);
    }

    public void setSender(String sender, Object... args) {
        this.sender = new AuditClientRequestParam(sender, args);
    }

    public void setReceiver(String receiver, Object... args) {
        this.receiver = new AuditClientRequestParam(receiver, args);
    }

    public void setContext(Object contextJson) {
        if (contextJson instanceof String) {
            this.context = (String) contextJson;
        } else if (contextJson == null) {
            this.context = null;
        } else {
            try {
                this.context = new ObjectMapper().writeValueAsString(contextJson);
            } catch (JsonProcessingException e) {
                log.error("Error on parsing context json", e);
                throw new UserException("audit.clientException.invalidContext");
            }
        }
    }

    @Override
    public String toString() {
        final String formatted = ", %s{value='%s', args=%s}";
        return "AuditClientRequest{" +
                "eventDate=" + eventDate +
                String.format(formatted, "eventType", paramValueToString(eventType), paramArgsToString(eventType)) +
                String.format(formatted, "objectType", paramValueToString(objectType), paramArgsToString(objectType)) +
                String.format(formatted, "objectId", paramValueToString(objectId), paramArgsToString(objectId)) +
                String.format(formatted, "objectName", paramValueToString(objectName), paramArgsToString(objectName)) +
                String.format(formatted, "userId", paramValueToString(userId), paramArgsToString(userId)) +
                String.format(formatted, "username", paramValueToString(username), paramArgsToString(username)) +
                String.format(formatted, "sourceWorkstation", paramValueToString(sourceWorkstation), paramArgsToString(sourceWorkstation)) +
                String.format(formatted, "sourceApplication", paramValueToString(sourceApplication), paramArgsToString(sourceApplication)) +
                ", context='" + context + "\'" +
                String.format(formatted, "hostname", paramValueToString(hostname), paramArgsToString(hostname)) +
                ", auditType='" + auditType + "\'" +
                String.format(formatted, "sender", paramValueToString(sender), paramArgsToString(sender)) +
                String.format(formatted, "receiver", paramValueToString(receiver), paramArgsToString(receiver)) +
                "}";
    }

    private String paramValueToString(AuditClientRequestParam param) {
        return param == null ? null : param.getValue();
    }

    private String paramArgsToString(AuditClientRequestParam param) {
        return param == null ? null : Arrays.toString(param.getArgs());
    }
}