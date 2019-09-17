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
public class AuditSourceApplicationCriteria extends RestCriteria {
    @ApiParam("Код системы")
    @QueryParam("code")
    private String code;
}