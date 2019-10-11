package ru.i_novus.ms.audit.client.impl.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.api.AuditRest;

public class Consumer {

    @Autowired
    @Qualifier("auditRestJaxRsProxyClient")
    private AuditRest auditRest;

    @JmsListener(destination = "${audit.client.queue}")
    public void receiveMessage(AuditForm request) {
        auditRest.add(request);
    }
}
