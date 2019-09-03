package ru.i_novus.ms.audit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "audit_object")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SequenceGenerator(name = "audit.audit_object_id_seq", allocationSize = 1)
public class AuditObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit.audit_object_id_seq")
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(length = 60)
    private String name;

    @Column(length = 60)
    private String type;

}
