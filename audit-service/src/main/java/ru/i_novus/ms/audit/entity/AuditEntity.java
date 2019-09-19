package ru.i_novus.ms.audit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Table(name = "audit")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString()
public class AuditEntity {
    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "object_type")
    private String auditObjectType;

    @Column(name = "object_name")
    private String auditObjectName;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "source_application")
    private String auditSourceApplication;

    @Column(name = "source_workstation")
    private String sourceWorkstation;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "hostname")
    @JsonProperty("hostname")
    private String hostname;

    @Column(name = "audit_type_id", nullable = false)
    private Short auditTypeId;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "receiver_id")
    private String receiverId;

    @PrePersist
    public void prePersist() {
        id = id == null ? UUID.randomUUID() : id;
        creationDate = creationDate == null ? LocalDateTime.now(Clock.systemUTC()) : creationDate;
    }

}
