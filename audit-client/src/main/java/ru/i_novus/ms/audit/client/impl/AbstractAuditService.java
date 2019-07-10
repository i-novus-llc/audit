package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;
import ru.i_novus.ms.audit.service.api.AuditControllerApi;

public abstract class AbstractAuditService implements AuditClient {

    AuditControllerApi auditControllerApi;

    RequestConverter requestConverter;

    @Autowired
    public void setAuditControllerApi(AuditControllerApi auditControllerApi) {
        this.auditControllerApi = auditControllerApi;
    }

    @Autowired
    public void setRequestConverter(RequestConverter requestConverter) {
        this.requestConverter = requestConverter;
    }
}