package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ru.i_novus.ms.audit.model.EventTypeCriteria;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/audit")
@Api("Ресурс аудита событий")
@ApiResponses({
        @ApiResponse(code = 200, message = "Событие"),
        @ApiResponse(code = 404, message = "Нет ресурса"),
        @ApiResponse(code = 500, message = "Ошибка и текст ошибки"),
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuditReferenceRest {

    @GET
    @Path("/objects")
    @ApiOperation("Получение объектов")
    Collection getObjects();

    @GET
    @Path("/eventType")
    @ApiOperation("Получение типов событий")
    Collection getEventType(@BeanParam EventTypeCriteria criteria);

    @GET
    @Path("/sourceApplications")
    @ApiOperation("Получение наименований систем")
    Collection getSourceApplications();
}
