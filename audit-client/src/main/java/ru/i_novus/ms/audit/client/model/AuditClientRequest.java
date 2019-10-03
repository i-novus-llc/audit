package ru.i_novus.ms.audit.client.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class AuditClientRequest implements Serializable {

    private LocalDateTime eventDate;

    private String eventType;

    private String objectType;

    private String objectId;

    private String objectName;

    private String userId;

    private String username;

    private String sourceWorkstation;

    private String sourceApplication;

    private String context;

    private String hostname;

    private Short auditType;

    private String sender;

    private String receiver;

    @Override
    public String toString() {
        return "AuditClientRequest{" +
                "eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", auditObjectType='" + objectType + '\'' +
                ", auditObjectId='" + objectId + '\'' +
                ", auditObjectName='" + objectName + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", sourceWorkstation='" + sourceWorkstation + '\'' +
                ", sourceApplication='" + sourceApplication + '\'' +
                ", context='" + context + '\'' +
                ", hostname='" + hostname + '\'' +
                ", auditType='" + auditType + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}