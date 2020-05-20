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
    @ApiModelProperty(value = "Дата наступления события (время должно быть в часовом поясе Москвы (UTC+3). Формат (пример) 2019-05-24T08:33:46.816",
            required = true)
    @NotNull
    private LocalDateTime eventDate;

    @ApiModelProperty(value = "Тип события (например, Создание, Редактирование, Удаление)",
            example = "Создание",
            required = true)
    @NotNull
    private String eventType;

    @ApiModelProperty(value = "Тип объекта, с которым произошло событие (например, role, case, organization и т.д.)",
            example = "Request")
    private String objectType;

    @ApiModelProperty(value = "Идентификатор объекта в исходной подсистеме (идентификатор конкретного значения, с которым произошло событие)",
            example = "b74c84d2-4d5c-4247-9792-830f27af3589")
    private String objectId;

    @ApiModelProperty(value = "Наименование объекта, с которым произошло событие (например, Роль, Случай обслуживания, Организация и т.д.)",
            example = "Заявка")
    private String objectName;

    @ApiModelProperty(value = "Идентификатор пользователя, выполнившего действие (событие)",
            example = "b153d0e5-8f3e-4e2d-a559-35fcc16c46ae")
    private String userId;

    @ApiModelProperty(value = "Логин пользователя, выполнившего действие (событие)",
            example = "example@example.org")
    private String username;

    @ApiModelProperty(value = "Код системы. в которой произошло событие (код, зарегистрированный в Единой подсистеме безопасности и авторизации)",
            example = "Archive")
    private String sourceApplication;

    @ApiModelProperty(value = "Наименование клиента, рабочей станции (пример, ip адрес)",
            example = "127.45.1.187")
    private String sourceWorkstation;

    @ApiModelProperty(value = "Контекст, информация о событии (Изменение данных, сообщение об ошибке и т.д.) в формате JSON",
            example = " \'{\'id\':4735,\'request_id\':5741,\'file_name\':\'protocol (2).zip\'," +
            "\'attachment_id\':\'df87052d-dc10-11e7-80b9-005056b1595b\',\'study_id\':null," +
                    "\'created_on_base\':false}\' ",
            required = true)
    @NotNull
    private String context;

    @ApiModelProperty(value = "Сервер, на котором произошло событие (пример, ip адрес)",
            example = "127.45.5.233")
    private String hostname;

    @ApiModelProperty(value = "Тип журнала (идентификатор справочника типов журналов " +
            "(1 - журнал действий пользователей, 2 - журнал интеграционнных сервисов, " +
            "3 - журнал авторизаций, сессий и неудачных попыток входа))",
            example = "1",
            required = true)
    @NotNull
    @Range(min = 1, max = 32767)
    private Short auditType;

    @ApiModelProperty(value = "Код (наименование) системы-отправителя (только для регистрации сообщения журнала интеграционных сервисов)",
            example = "Archive")
    private String sender;

    @ApiModelProperty(value = "Код (наименование) системы-получателя (только для регистрации сообщения журнала интеграционных сервисов)",
            example = "Rdm")
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
