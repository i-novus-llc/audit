package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Справочник типов журналов")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditType {
    @ApiModelProperty("Идентификатор типа журнала")
    private Short id;

    @ApiModelProperty("Наименование")
    private String name;

}
