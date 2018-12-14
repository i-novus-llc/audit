package ru.i_novus.ms.audit.entity;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit", schema = "audit" )
@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
public class AuditEntity {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "object_type", nullable = false)
    private String objectType;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "object_name")
    private String objectName;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "source_application")
    private String sourceApplication;

    @Column(name = "source_workstation")
    private String sourceWorkstation;

    @Column(name = "context", columnDefinition = "json")
    @Type(type = "JsonbType")
    private String context;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    public String getSourceWorkstation() {
        return sourceWorkstation;
    }

    public void setSourceWorkstation(String sourceWorkstation) {
        this.sourceWorkstation = sourceWorkstation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        // todo
        if (creationDate == null) {
            creationDate = now;
        }
    }
}
