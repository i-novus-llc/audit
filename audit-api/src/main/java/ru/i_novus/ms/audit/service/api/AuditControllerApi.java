package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteriaDTO;
import ru.i_novus.ms.audit.model.AuditForm;

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
public interface AuditControllerApi {

    @GET
    @Path("/{id}")
    @ApiOperation("Поиск события по идентификатору")
    @Produces(MediaType.APPLICATION_JSON)
    Audit getById(@ApiParam("Идентификатор события") @PathParam("id") UUID id);

    @GET
    @ApiOperation("Поиск событий")
    @Produces(MediaType.APPLICATION_JSON)
    Page<Audit> search(@ApiParam("Критерии поиска события") @BeanParam AuditCriteriaDTO criteria);

    @POST
    @ApiOperation("Добавить событие")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Audit add(@ApiParam("Событие") @RequestBody AuditForm request);
}
