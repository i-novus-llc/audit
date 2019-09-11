package ru.i_novus.ms.audit.builder.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.model.AuditObject;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditObjectBuilder {

    public static AuditObject buildByEntity(AuditObjectEntity entity) {
        return AuditObject.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
