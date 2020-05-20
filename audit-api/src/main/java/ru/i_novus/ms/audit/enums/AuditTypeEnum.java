package ru.i_novus.ms.audit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Нумератор типов журналов
 *
 * @author agaifutdinov
 */
@Getter
@AllArgsConstructor
public enum AuditTypeEnum {
    ACTION_JOURNAL((short) 1),
    INTEGRATION_JOURNAL((short) 2),
    AUTH_JOURNAL((short) 3);

    private Short id;
}
