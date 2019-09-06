package ru.i_novus.ms.audit.builder.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;
import ru.i_novus.ms.audit.model.AuditType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditTypeBuilder {

    public static AuditType buildByEntity (AuditTypeEntity entity) {
        return  AuditType
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
