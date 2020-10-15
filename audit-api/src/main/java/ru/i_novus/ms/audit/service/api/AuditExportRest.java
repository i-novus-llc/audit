/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.service.api;

import io.swagger.annotations.*;
import ru.i_novus.ms.audit.criteria.AuditCriteria;

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
@ApiImplicitParams({
        @ApiImplicitParam(name = "sort", value = "Сортировка, sort=<код атрибута>, <asc|desc>", dataType = "string", paramType = "query")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuditExportRest {

    @GET
    @Path("/export")
    @ApiOperation("Выгрузка журнала аудита по критериям")
    void auditExport(@BeanParam AuditCriteria auditCriteria) throws IOException;
}