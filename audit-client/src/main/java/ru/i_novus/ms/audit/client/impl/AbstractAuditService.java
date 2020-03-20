package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;

public abstract class AbstractAuditService implements AuditClient {

    RequestConverter requestConverter;

    @Autowired
    public void setRequestConverter(RequestConverter requestConverter) {
        this.requestConverter = requestConverter;
    }
}