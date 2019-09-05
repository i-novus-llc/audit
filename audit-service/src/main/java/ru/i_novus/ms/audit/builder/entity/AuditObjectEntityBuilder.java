package ru.i_novus.ms.audit.builder.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditObjectEntityBuilder {
    public static AuditObjectEntity buildEntity(String name, String type) {
        return AuditObjectEntity
                .builder()
                .name(name)
                .type(type)
                .build();
    }

}
