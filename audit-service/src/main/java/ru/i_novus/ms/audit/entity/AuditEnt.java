package ru.i_novus.ms.audit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
@Getter
@Setter
@Builder
@Table(name = "audit")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"auditObjectTypes", "auditObjectNames", "auditSourceApplication"})
public class AuditEnt {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @ManyToOne
    @JoinColumn(name = "object_type_id", nullable = false)
    private AuditObjectType auditObjectType;

    @ManyToOne
    @JoinColumn(name = "object_name_id", nullable = false)
    private AuditObjectName auditObjectName;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "source_application_id", nullable = false)
    private AuditSourceApplication auditSourceApplication;

    @Column(name = "source_workstation")
    private String sourceWorkstation;

    @Column(name = "context", columnDefinition = "json")
    @Type(type = "JsonbType")
    private String context;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "hostname")
    @JsonProperty("hostname")
    private String hostname;

    @PrePersist
    public void prePersist() {
        id = id == null? UUID.randomUUID(): id;
        creationDate = creationDate == null? LocalDateTime.now(Clock.systemUTC()) : creationDate;
    }

}
