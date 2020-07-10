package ru.i_novus.ms.audit.entity;

import lombok.*;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@Table(name = "audit_event_type")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@SQLInsert(sql = "insert into audit.audit_event_type (audit_type_id, name, id) values(?, ?, ?)")
public class AuditEventTypeEntity {
    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "audit_type_id", nullable = false)
    private Short auditTypeId;

    @PrePersist
    public void prePersist() {
        id = id == null ? UUID.randomUUID() : id;
    }

}
