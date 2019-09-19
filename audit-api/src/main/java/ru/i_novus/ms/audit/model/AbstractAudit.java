package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class AbstractAudit implements Serializable {
    @ApiModelProperty(value = "Дата события", required = true)
    @NotNull
    private LocalDateTime eventDate;

    @ApiModelProperty(value = "Тип события", required = true)
    @NotNull
    private String eventType;

    @ApiModelProperty(value = "Тип объекта")
    private String objectType;

    @ApiModelProperty(value = "Идентификатор объекта")
    private String objectId;

    @ApiModelProperty(value = "Наименование объекта")
    private String objectName;

    @ApiModelProperty(value = "Идентификатор пользователя", required = true)
    @NotNull
    private String userId;

    @ApiModelProperty(value = "Имя пользователя", required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "Имя программы")
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
    @Range(min = 1, max = 32767)
    private Short auditType;

    @ApiModelProperty(value = "Код (наименование) системы-отправителя")
    private String sender;

    @ApiModelProperty(value = "Код (наименование) системы-получателя")
    private String receiver;

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
                ", auditType='" + auditType + '\'' +
                '}';
    }
}
