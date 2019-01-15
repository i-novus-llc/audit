package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.i_novus.ms.audit.client.impl.queue.Producer;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;

import static ru.i_novus.ms.audit.client.AuditClientConfiguration.AUDIT_QUEUE;

public class AsyncAuditClientImpl extends AbstractAuditService {

    private Producer producer;

    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void add(AuditClientRequest request) {
        producer.send(AUDIT_QUEUE, requestConverter.toAuditRequest(request));
    }
}