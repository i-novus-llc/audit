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

    private String context;

    private String hostname;

    private Short auditTypeId;

    @Override
    public String toString() {
        return "AuditClientRequest{" +
                "eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", auditObjectTypes='" + objectType + '\'' +
                ", auditObjectId='" + objectId + '\'' +
                ", auditObjectNames='" + objectName + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}