package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditForm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/audit")
@Api("Аудит событий")
@ApiResponses({
        @ApiResponse(code = 200, message = "Событие"),
        @ApiResponse(code = 404, message = "Нет ресурса"),
        @ApiResponse(code = 500, message = "Ошибка и текст ошибки"),
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuditRest {

    @GET
    @Path("/{id}")
    @ApiOperation("Поиск события по идентификатору")
    Audit getById(@ApiParam("Идентификатор события") @PathParam("id") UUID id);

    @GET
    @ApiOperation("Поиск событий")
    Page<Audit> search(@BeanParam @Valid AuditCriteria criteria);

    @POST
    @ApiOperation("Добавить событие")
    Audit add(@ApiParam("Событие") @Valid AuditForm request);

    @POST
    @Path("/sso-events-synchronize")
    @ApiOperation("Синхронизация журнала авторизаций")
    void startEventsSynchronize();
}
