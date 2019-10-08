package ru.i_novus.ms.audit.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class OpenIdEventLogCriteria {
    @QueryParam("dateFrom")
    private LocalDateTime dateFrom;

    @QueryParam("first")
    private Integer first;

    @QueryParam("max")
    private Integer max;
}
