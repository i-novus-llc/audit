package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuditCriteria extends RestCriteria {

    @QueryParam("id")
    private String id;

    @ApiParam(value = "Дата события (от)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateFrom")
    private LocalDateTime eventDateFrom;

    @ApiParam(value = "Дата события (до)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateTo")
    private LocalDateTime eventDateTo;

    @ApiParam("Тип события")
    @QueryParam("eventType")
    private String eventType;

    @ApiParam(value = "Объект")
    @QueryParam("auditObjects")
    private String[] objectType;

    @ApiParam("Идентификатор объекта")
    @QueryParam("auditObjectId")
    private String objectId;

    @ApiParam("Тип события")
    @QueryParam("auditEventTypes")
    private String[] auditEventType;

    @ApiParam("Идентификатор пользователя")
    @QueryParam("userId")
    private String userId;

    @ApiParam("Имя пользователя")
    @QueryParam("username")
    private String username;

    @ApiParam("Имя программы")
    @QueryParam("auditSourceApplications")
    private String[] sourceApplication;

    @ApiParam("Рабочая станция")
    @QueryParam("auditSourceWorkstations")
    private String sourceWorkstation;

    @ApiParam("Контекст")
    @QueryParam("context")
    private String context;

    @ApiParam("Имя хоста")
    @QueryParam("hostname")
    private String hostname;

    @ApiParam("Идентификатор типа журнала")
    @QueryParam("auditTypeId")
    private Short[] auditTypeId;

    @ApiParam("Код системы отправителя")
    @QueryParam("senders")
    private String[] sender;

    @ApiParam("Код системы получателя")
    @QueryParam("receivers")
    private String[] receiver;

    @ApiParam("Поле сортировки")
    @QueryParam("sortingColumn")
    private String sortingColumn;

    @ApiParam(value = "Порядок сортировки", allowableValues = "DESC, ASC")
    @QueryParam("sortingOrder")
    private String sortingOrder;
}
