package ru.i_novus.ms.audit.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.AuditService;

public class Consumer {

    @Autowired
    private AuditService auditService;

    @JmsListener(destination = "${audit.client.queue}")
    public void receiveMessage(AuditForm request) {
        auditService.create(request);
    }
}