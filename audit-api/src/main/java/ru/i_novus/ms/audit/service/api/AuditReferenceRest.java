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
import org.springframework.data.domain.Page;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.model.AuditSourceApplication;
import ru.i_novus.ms.audit.model.AuditType;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "Сортировка, sort=<код атрибута>, <asc|desc>", dataType = "string", paramType = "query")
    })
    Page<AuditObject> getObjects(@BeanParam AuditObjectCriteria criteria);

    @GET
    @Path("/eventType")
    @ApiOperation("Получение типов событий")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "Сортировка, sort=<код атрибута>, <asc|desc>", dataType = "string", paramType = "query")
    })
    Page<AuditEventType> getEventType(@BeanParam AuditEventTypeCriteria criteria);

    @GET
    @Path("/sourceApplications")
    @ApiOperation("Получение наименований систем")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "Сортировка, sort=<код атрибута>, <asc|desc>", dataType = "string", paramType = "query")
    })
    Page<AuditSourceApplication> getSourceApplications(@BeanParam AuditSourceApplicationCriteria criteria);

    @GET
    @Path("/auditTypes")
    @ApiOperation("Получение типов журналов")
    Collection<AuditType> getAuditTypes();
}
