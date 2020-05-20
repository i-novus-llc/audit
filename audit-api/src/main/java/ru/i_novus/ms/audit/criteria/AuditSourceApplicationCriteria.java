package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.QueryParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditSourceApplicationCriteria extends AuditRestCriteria {
    @ApiParam("Код системы")
    @QueryParam("code")
    private String code;
}
