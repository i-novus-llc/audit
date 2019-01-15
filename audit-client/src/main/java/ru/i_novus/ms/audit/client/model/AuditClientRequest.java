package ru.i_novus.ms.audit.client.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditClientRequest implements Serializable {
    
    private LocalDateTime eventDate;

    private String eventType;

    private String objectType;

    private String objectId;

    private String objectName;

    private String context;

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AuditClientRequest{" +
                "eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", objectType='" + objectType + '\'' +
                ", objectId='" + objectId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}