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

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.SimpleAuditClientImpl;
import ru.i_novus.ms.audit.service.api.AuditRest;

@Configuration
@ComponentScan({"ru.i_novus.ms.audit.client.impl", "ru.i_novus.ms.audit.client.util.json"})
@EnableJaxRsProxyClient(classes = AuditRest.class, address = "${audit.service.url}")
public class AuditSimpleClientAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuditClient simpleAuditClient(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        SimpleAuditClientImpl simpleAuditClient = new SimpleAuditClientImpl();
        simpleAuditClient.setAuditRest(auditRest);
        return simpleAuditClient;
    }
}
