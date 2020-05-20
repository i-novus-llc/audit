package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.service.api.AuditRest;

public class SimpleAuditClientImpl extends AbstractAuditService {

    AuditRest auditRest;

    @Autowired
    public void setAuditRest(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    @Override
    public void add(AuditClientRequest request) {
        auditRest.add(requestConverter.toAuditRequest(request));
    }
}