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
