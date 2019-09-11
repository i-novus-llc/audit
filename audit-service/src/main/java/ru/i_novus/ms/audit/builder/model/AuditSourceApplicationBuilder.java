package ru.i_novus.ms.audit.builder.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.model.AuditSourceApplication;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditSourceApplicationBuilder {

    public static AuditSourceApplication buildByEntity (AuditSourceApplicationEntity entity) {
        return  AuditSourceApplication
                .builder()
                .id(entity.getId())
                .code(entity.getCode())
                .build();
    }
}
