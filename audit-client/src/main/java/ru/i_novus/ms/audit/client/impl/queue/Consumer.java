package ru.i_novus.ms.audit.client.impl.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.api.AuditRest;

import static ru.i_novus.ms.audit.client.AuditClientConfiguration.AUDIT_QUEUE;

@Component
public class Consumer {

    @Autowired
    private AuditRest auditRest;

    @Autowired
    public Consumer(AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    @JmsListener(destination = AUDIT_QUEUE)
    public void receiveMessage(AuditForm request) {
        auditRest.add(request);
    }
}
