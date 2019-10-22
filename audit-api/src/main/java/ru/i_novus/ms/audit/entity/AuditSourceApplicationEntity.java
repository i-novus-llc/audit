package ru.i_novus.ms.audit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "audit_source_application")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "audit.audit_source_application_id_seq", allocationSize = 1)
public class AuditSourceApplicationEntity {
    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit.audit_source_application_id_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(length = 60)
    private String code;

}
