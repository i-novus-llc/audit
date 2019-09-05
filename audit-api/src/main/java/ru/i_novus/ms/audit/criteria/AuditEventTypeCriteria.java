package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;

import javax.ws.rs.QueryParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditEventTypeCriteria extends RestCriteria {
    @ApiParam("Наименование")
    @QueryParam("name")
    private String name;

    @ApiParam("Идентификатор типа журнала")
    @QueryParam("auditTypeId")
    private Short auditTypeId;
}
