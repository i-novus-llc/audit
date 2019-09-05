package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Справочник систем/модулей")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditSourceApplication {
    @ApiModelProperty("Идентификатор события")
    private Integer id;

    @ApiModelProperty("Код")
    private String code;
}
