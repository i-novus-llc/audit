package ru.i_novus.ms.audit.client.impl;

import ru.i_novus.ms.audit.client.model.AuditClientRequest;

public class SimpleAuditClientImpl extends AbstractAuditService {

    @Override
    public void add(AuditClientRequest request) {
        auditService.add(requestConverter.toAuditRequest(request));
    }
}