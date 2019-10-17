package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Validated
public class AuditCriteria extends RestCriteria {

    @QueryParam("id")
    private String id;

    @ApiParam(value = "Дата события (от)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateFrom")
    @NotNull
    private LocalDateTime eventDateFrom;

    @ApiParam(value = "Дата события (до)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateTo")
    @NotNull
    private LocalDateTime eventDateTo;

    @ApiParam("Тип события")
    @QueryParam("eventType")
    private String eventType;

    @ApiParam(value = "Объект")
    @QueryParam("objectType")
    private String[] objectType;

    @ApiParam(value = "Наименование объекта")
    @QueryParam("objectName")
    private String[] objectName;

    @ApiParam("Идентификатор объекта")
    @QueryParam("objectId")
    private String objectId;

    @ApiParam("Тип события")
    @QueryParam("eventType")
    private String[] auditEventType;

    @ApiParam("Идентификатор пользователя")
    @QueryParam("userId")
    private String userId;

    @ApiParam("Имя пользователя")
    @QueryParam("username")
    private String username;

    @ApiParam("Имя программы")
    @QueryParam("sourceApplication")
    private String[] sourceApplication;

    @ApiParam("Рабочая станция")
    @QueryParam("sourceWorkstation")
    private String sourceWorkstation;

    @ApiParam("Контекст")
    @QueryParam("context")
    private String context;

    @ApiParam("Имя хоста")
    @QueryParam("hostname")
    private String hostname;

    @ApiParam("Идентификатор типа журнала")
    @QueryParam("auditType")
    @NotNull
    private Short auditTypeId;

    @ApiParam("Код системы отправителя")
    @QueryParam("sender")
    private String[] sender;

    @ApiParam("Код системы получателя")
    @QueryParam("receiver")
    private String[] receiver;

    @ApiParam("Поле сортировки")
    @QueryParam("sortingColumn")
    private String sortingColumn;

    @ApiParam(value = "Порядок сортировки", allowableValues = "DESC, ASC")
    @QueryParam("sortingOrder")
    private String sortingOrder;
}
