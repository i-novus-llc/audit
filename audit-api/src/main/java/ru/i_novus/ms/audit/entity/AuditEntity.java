/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.entity;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.SQLInsert;

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
@SQLInsert(sql = "insert into audit.audit (object_name, object_type, source_application, audit_type_id, context, " +
        "creation_date, event_date, event_type, hostname, object_id, receiver_id, sender_id, source_workstation, " +
        "user_id, username, id) " +
        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
public class AuditEntity {
    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "id", insertable = false, updatable = false)
    @ColumnTransformer(read = "id::text")
    private String idAsText;

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

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
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
    private String hostname;

    @Column(name = "audit_type_id")
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
