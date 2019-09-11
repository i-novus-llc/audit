package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

@ApiModel("Справочник типов событий")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEventType {
    @ApiModelProperty("Идентификатор события")
    private UUID id;

    @ApiModelProperty("Наименование")
    private String name;

}
