package ru.i_novus.ms.audit.client.impl.queue;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import ru.i_novus.ms.audit.model.AuditForm;

@Log4j
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String queueName, AuditForm request) {
        try {
            jmsTemplate.convertAndSend(queueName, request);
        } catch (JmsException e) {
            log.error("Can't process audit event: " + request.toString(), e);
            throw e;
        }
    }
}