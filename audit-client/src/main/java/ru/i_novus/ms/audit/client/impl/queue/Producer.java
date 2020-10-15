/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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