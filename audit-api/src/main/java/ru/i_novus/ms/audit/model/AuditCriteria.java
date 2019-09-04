package ru.i_novus.ms.audit.model;

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

    @ApiParam(value = "Дата события (от)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateFrom")
    private LocalDateTime eventDateFrom;

    @ApiParam(value = "Дата события (до)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateTo")
    private LocalDateTime eventDateTo;

    @ApiParam("Тип события")
    @QueryParam("eventType")
    private String eventType;

    @ApiParam(value = "Тип объекта")
    @QueryParam("auditObjectTypes")
    private String[] objectType;

    @ApiParam("Идентификатор объекта")
    @QueryParam("auditObjectId")
    private String objectId;

    @ApiParam("Наименование объекта")
    @QueryParam("auditObjectNames")
    private String[] objectName;

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

    @ApiParam("Поле сортировки")
    @QueryParam("sortingColumn")
    private String sortingColumn;

    @ApiParam(value = "Порядок сортировки", allowableValues = "DESC, ASC")
    @QueryParam("sortingOrder")
    private String sortingOrder;
}
