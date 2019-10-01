package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.i_novus.ms.audit.client.impl.queue.Producer;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;

public class AsyncAuditClientImpl extends AbstractAuditService {

    private Producer producer;

    @Value("${audit.client.queue}")
    private String auditQueue;

    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void add(AuditClientRequest request) {
        producer.send(auditQueue, requestConverter.toAuditRequest(request));
    }
}