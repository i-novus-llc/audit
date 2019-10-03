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
