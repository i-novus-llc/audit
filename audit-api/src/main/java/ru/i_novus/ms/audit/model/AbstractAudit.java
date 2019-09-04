package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public abstract class AbstractAudit implements Serializable {

    @ApiModelProperty(value = "Дата события", required = true)
    @NotNull
    private LocalDateTime eventDate;

    @ApiModelProperty(value = "Тип события", required = true)
    @NotNull
    private String eventType;

    @ApiModelProperty(value = "Тип объекта", required = true)
    @NotNull
    private String objectType;

    @ApiModelProperty(value = "Идентификатор объекта", required = true)
    @NotNull
    private String objectId;

    @ApiModelProperty(value = "Наименование объекта", required = true)
    @NotNull
    private String objectName;

    @ApiModelProperty(value = "Идентификатор пользователя", required = true)
    @NotNull
    private String userId;

    @ApiModelProperty(value = "Имя пользователя", required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "Имя программы", required = true)
    @NotNull
    private String sourceApplication;

    @ApiModelProperty(value = "Рабочая станция")
    private String sourceWorkstation;

    @ApiModelProperty(value = "Контекст", required = true, example = " \'{\'id\':4735,\'request_id\':5741,\'file_name\':\'protocol (2).zip\'," +
            "\'attachment_id\':\'df87052d-dc10-11e7-80b9-005056b1595b\',\'study_id\':null," +
            "\'created_on_base\':false}\' ")
    @NotNull
    private String context;

    @ApiModelProperty(value = "Сервер")
    private String hostname;

    @ApiModelProperty(value = "Идентификатор типа журнала", required = true)
    @NotNull
    private Short auditTypeId;

    @Override
    public String toString() {
        return "AbstractAudit{" +
                "eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", auditObjectTypes='" + objectType + '\'' +
                ", auditObjectId='" + objectId + '\'' +
                ", auditObjectNames='" + objectName + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", auditSourceApplication='" + sourceApplication + '\'' +
                ", auditSourceWorkstations='" + sourceWorkstation + '\'' +
                ", context='" + context + '\'' +
                ", auditTypeId='" + auditTypeId + '\'' +
                '}';
    }
}
