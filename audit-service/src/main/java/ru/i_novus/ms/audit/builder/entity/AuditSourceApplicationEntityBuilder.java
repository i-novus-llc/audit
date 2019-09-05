package ru.i_novus.ms.audit.builder.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditSourceApplicationEntityBuilder {

    public static AuditSourceApplicationEntity buildEntity (String code) {
        return AuditSourceApplicationEntity
                .builder()
                .code(code)
                .build();
    }
}
