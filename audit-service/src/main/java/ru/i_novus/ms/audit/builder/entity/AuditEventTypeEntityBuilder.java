package ru.i_novus.ms.audit.builder.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditEventTypeEntityBuilder {

    public static AuditEventTypeEntity buildEntity(String name, Short auditTypeId) {
        return AuditEventTypeEntity
                .builder()
                .name(name)
                .auditTypeId(auditTypeId)
                .build();
    }
}
