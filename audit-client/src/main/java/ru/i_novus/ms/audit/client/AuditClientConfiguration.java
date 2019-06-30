package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import ru.i_novus.ms.audit.client.impl.AsyncAuditClientImpl;
import ru.i_novus.ms.audit.client.impl.SimpleAuditClientImpl;
import ru.i_novus.ms.audit.rest.AuditRest;
import ru.i_novus.ms.audit.service.api.AuditControllerApi;

@EnableJms
@SpringBootConfiguration
@ComponentScan("ru.i_novus.ms.audit.client")
@EnableJaxRsProxyClient(classes = AuditControllerApi.class, address = "${audit.url}")
public class AuditClientConfiguration {

    public static final String AUDIT_QUEUE = "audit.queue";

    @Bean("auditServiceJaxRsProxyClient")
    public AuditControllerApi auditControllerApi(){
        return new AuditRest();
    }

    @Bean
    public AuditClient asyncAuditClient(@Qualifier("auditServiceJaxRsProxyClient") AuditControllerApi auditControllerApi) {
        AsyncAuditClientImpl asyncAuditClient = new AsyncAuditClientImpl();
        asyncAuditClient.setAuditControllerApi(auditControllerApi);
        return asyncAuditClient;
    }

    @Bean
    public AuditClient simpleAuditClient(@Qualifier("auditServiceJaxRsProxyClient") AuditControllerApi auditControllerApi) {
        SimpleAuditClientImpl simpleAuditClient = new SimpleAuditClientImpl();
        simpleAuditClient.setAuditControllerApi(auditControllerApi);
        return simpleAuditClient;
    }
}