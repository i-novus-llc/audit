package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.*;
import ru.i_novus.ms.audit.criteria.AuditCriteria;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/audit")
@Api("Сервис выгрузки журналов аудита")
@ApiResponses({
        @ApiResponse(code = 200, message = "Событие"),
        @ApiResponse(code = 404, message = "Нет ресурса"),
        @ApiResponse(code = 500, message = "Ошибка и текст ошибки"),
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuditExportRest {

    @GET
    @Path("/export")
    @ApiOperation("Выгрузка журнала аудита по критериям")
    void auditExport(@BeanParam AuditCriteria auditCriteria) throws IOException;
}