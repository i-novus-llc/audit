package ru.i_novus.ms.audit.client.impl.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.AuditRequest;
import ru.i_novus.ms.audit.service.api.AuditService;

import static ru.i_novus.ms.audit.client.AuditClientConfiguration.AUDIT_QUEUE;

@Component
public class Consumer {

    private AuditService auditService;

    @Autowired
    public Consumer(AuditService auditService) {
        this.auditService = auditService;
    }

    @JmsListener(destination = AUDIT_QUEUE)
    public void receiveMessage(AuditRequest request) {
        auditService.add(request);
    }
}
