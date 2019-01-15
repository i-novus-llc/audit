package ru.i_novus.ms.audit.client.impl.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.AuditRequest;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private JmsTemplate jmsTemplate;

    @Autowired
    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String queueName, AuditRequest request) {
        try {
            jmsTemplate.convertAndSend(queueName, request);
        } catch (JmsException e) {
            logger.error("Can't process audit event: " + request.toString(), e);
            throw e;
        }
    }
}