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

package ru.i_novus.ms.audit.client.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.AsyncAuditClientImpl;
import ru.i_novus.ms.audit.client.impl.queue.Producer;

@EnableJms
@Configuration
@ComponentScan({"ru.i_novus.ms.audit.client.impl", "ru.i_novus.ms.audit.client.util.json"})
@PropertySource("classpath:/META-INF/ru/i_novus/ms/audit/client/autoconfigure/async-client.properties")
public class AuditAsyncClientAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Producer auditClientProducer() {
        return new Producer();
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuditClient asyncAuditClient() {
        return new AsyncAuditClientImpl();
    }
}
