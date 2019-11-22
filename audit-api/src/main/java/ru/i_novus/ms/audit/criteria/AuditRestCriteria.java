package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;

import javax.ws.rs.QueryParam;

/**
 * @author akuznetcov
 **/
@Getter
@Setter
public class AuditRestCriteria extends RestCriteria {

    @ApiParam("Поле сортировки")
    @QueryParam("sortingColumn")
    private String sortingColumn;

    @ApiParam(value = "Порядок сортировки", allowableValues = "DESC, ASC")
    @QueryParam("sortingOrder")
    private String sortingOrder;
}