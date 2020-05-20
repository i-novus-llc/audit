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
