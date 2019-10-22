package ru.i_novus.ms.audit.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "audit_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTypeEntity {
    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(targetEntity = AuditEntity.class, mappedBy = "auditType", fetch = FetchType.LAZY)
    private Set<AuditEntity> auditEntities;
}
