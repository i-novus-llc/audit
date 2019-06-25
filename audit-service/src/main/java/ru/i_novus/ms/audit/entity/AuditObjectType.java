package ru.i_novus.ms.audit.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "audit_object_type")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuditObjectType {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(length = 60)
    private String name;

    @OneToMany(mappedBy = "auditObjectType")
    @JsonIgnore
    private Set<AuditEnt> auditEntities;

    @PrePersist
    public void prePersist() {
        id = id == null ? UUID.randomUUID() : id;
    }
}
