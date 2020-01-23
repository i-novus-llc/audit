package ru.i_novus.ms.audit.builder.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;

/**
 * Билдер для типов журналов
 *
 * @author agaifutdinov
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditTypeEntityBuilder {

    /**
     * Получение новой сущности для несуществующего журнала
     *
     * @param id идентификатор типа журнала
     * @return сущность несуществующего типа журнала
     */
    public static AuditTypeEntity buildDefaultAuditType(Short id) {
        return AuditTypeEntity.builder()
                .id(id)
                .code("UNKNOWN")
                .name("UNKNOWN")
                .build();
    }
}
