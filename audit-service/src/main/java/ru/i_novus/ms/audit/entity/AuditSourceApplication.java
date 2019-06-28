package ru.i_novus.ms.audit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "audit_source_application")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuditSourceApplication {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "auditSourceApplication")
    @JsonIgnore
    private Set<AuditEntity> auditEntities;

    @PrePersist
    public void prePersist() {
        id = id == null ? UUID.randomUUID() : id;
    }
}
