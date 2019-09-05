package ru.i_novus.ms.audit.builder.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditEventTypeBuilder {

    public static AuditEventType buildByEntity(AuditEventTypeEntity entity) {
        return  AuditEventType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
