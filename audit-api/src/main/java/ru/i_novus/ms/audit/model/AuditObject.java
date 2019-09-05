package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Справочник объектов")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditObject {
    @ApiModelProperty("Идентификатор события")
    private Integer id;

    @ApiModelProperty("Наименование")
    private String name;
}
