package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/audit")
@Api("Аудит событий")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuditService {

    @GET
    @Path("/{id}")
    @ApiOperation("Поиск события по идентификатору")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Событие"),
            @ApiResponse(code = 404, message = "Нет ресурса")
    })
    Audit getById(@ApiParam("Идентификатор события") @PathParam("id") UUID id);

    @GET
    @ApiOperation("Поиск событий")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Список событий"),
            @ApiResponse(code = 404, message = "Нет ресурса"),
    })
    Page<Audit> search(@ApiParam("Критерии поиска события") @BeanParam AuditCriteria criteria);

    @POST
    @ApiOperation("Добавить событие")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Событие сохранено"),
            @ApiResponse(code = 404, message = "Нет ресурса"),
    })
    Audit add(@ApiParam("Событие") AuditRequest request);
}
