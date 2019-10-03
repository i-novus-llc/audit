package ru.i_novus.ms.audit.client.impl.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.api.AuditRest;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private AuditRest auditRest;

    @Autowired
    public Consumer(AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    @JmsListener(destination = "${audit.client.queue}")
    public void receiveMessage(AuditForm request) {
        logger.info("message received");
        auditRest.add(request);
    }
}
