package ru.i_novus.ms.audit.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.AuditService;
import ru.i_novus.ms.audit.service.api.AuditRest;

public class Consumer {

    @Autowired
    @Qualifier("auditRestJaxRsProxyClient")
    private AuditRest auditRest;

    @Autowired
    private AuditService auditService;

    @JmsListener(destination = "${audit.client.queue}")
    public void receiveMessage(AuditForm request) {
        auditService.create(request);
    }
}