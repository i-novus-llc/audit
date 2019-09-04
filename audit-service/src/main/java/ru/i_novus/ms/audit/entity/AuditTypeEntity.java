package ru.i_novus.ms.audit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "audit_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "audit.audit_type_id_seq", allocationSize = 1)
public class AuditTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit.audit_type_id_seq")
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false)
    private String name;
}
