package ru.i_novus.ms.audit.service.api;

import ru.i_novus.ms.audit.criteria.OpenIdEventLogCriteria;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OpenIdEventLogRest {
    @GET
    @Path("/events")
    List<OpenIdEventLog> get(@BeanParam OpenIdEventLogCriteria eventCriteria);
}
