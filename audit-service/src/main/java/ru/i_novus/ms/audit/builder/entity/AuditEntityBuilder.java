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

package ru.i_novus.ms.audit.builder.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.model.AuditForm;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditEntityBuilder {

    public static AuditEntity buildEntity(AuditForm form) {
        return AuditEntity.builder()
                .auditObjectName(form.getObjectName())
                .auditObjectType(form.getObjectType())
                .auditSourceApplication(form.getSourceApplication())
                .context(form.getContext())
                .eventDate(form.getEventDate())
                .eventType(form.getEventType())
                .userId(form.getUserId())
                .username(form.getUsername())
                .objectId(form.getObjectId())
                .sourceWorkstation(form.getSourceWorkstation())
                .hostname(form.getHostname())
                .auditTypeId(form.getAuditType())
                .senderId(form.getSender())
                .receiverId(form.getReceiver())
                .build();
    }
}
