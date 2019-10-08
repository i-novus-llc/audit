package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;
import ru.i_novus.ms.audit.service.api.AuditRest;

public abstract class AbstractAuditService implements AuditClient {

    AuditRest auditRest;

    RequestConverter requestConverter;

    @Autowired
    public void setAuditRest(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    @Autowired
    public void setRequestConverter(RequestConverter requestConverter) {
        this.requestConverter = requestConverter;
    }
}