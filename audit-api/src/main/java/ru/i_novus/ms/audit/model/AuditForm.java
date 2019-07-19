package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Запрос на добавление события")
@Getter
@Setter
@AllArgsConstructor
public class AuditForm extends AbstractAudit {
}
