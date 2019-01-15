package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import ru.i_novus.ms.audit.client.impl.AsyncAuditClientImpl;
import ru.i_novus.ms.audit.client.impl.SimpleAuditClientImpl;
import ru.i_novus.ms.audit.service.api.AuditService;

@EnableJms
@SpringBootConfiguration
@ComponentScan("ru.i_novus.ms.audit.client")
@EnableJaxRsProxyClient(classes = AuditService.class, address = "${audit.url}")
public class AuditClientConfiguration {

    public static final String AUDIT_QUEUE = "audit.queue";

    @Bean
    public AuditClient asyncAuditClient(@Qualifier("auditServiceJaxRsProxyClient") AuditService auditService) {
        AsyncAuditClientImpl asyncAuditClient = new AsyncAuditClientImpl();
        asyncAuditClient.setAuditService(auditService);
        return asyncAuditClient;
    }

    @Bean
    public AuditClient simpleAuditClient(@Qualifier("auditServiceJaxRsProxyClient") AuditService auditService) {
        SimpleAuditClientImpl simpleAuditClient = new SimpleAuditClientImpl();
        simpleAuditClient.setAuditService(auditService);
        return simpleAuditClient;
    }
}