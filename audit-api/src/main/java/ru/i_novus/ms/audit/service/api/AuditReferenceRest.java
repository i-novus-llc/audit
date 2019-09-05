package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.model.AuditSourceApplication;

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
    Page<AuditObject> getObjects(@BeanParam AuditObjectCriteria criteria);

    @GET
    @Path("/eventType")
    @ApiOperation("Получение типов событий")
    Page<AuditEventType> getEventType(@BeanParam AuditEventTypeCriteria criteria);

    @GET
    @Path("/sourceApplications")
    @ApiOperation("Получение наименований систем")
    Page<AuditSourceApplication> getSourceApplications(@BeanParam AuditSourceApplicationCriteria criteria);

    @GET
    @Path("/auditTypes")
    @ApiOperation("Получение типов журналов")
    Collection getAuditTypes();
}
