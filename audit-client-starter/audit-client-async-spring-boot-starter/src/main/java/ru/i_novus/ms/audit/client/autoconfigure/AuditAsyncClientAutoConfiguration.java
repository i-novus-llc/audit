package ru.i_novus.ms.audit.client.autoconfigure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.impl.AsyncAuditClientImpl;
import ru.i_novus.ms.audit.client.impl.queue.Consumer;
import ru.i_novus.ms.audit.client.impl.queue.Producer;
import ru.i_novus.ms.audit.service.api.AuditRest;

@EnableJms
@Configuration
@ComponentScan("ru.i_novus.ms.audit.client")
@PropertySource("classpath:/META-INF/ru/i_novus/ms/audit/client/autoconfigure/async-client.properties")
public class AuditAsyncClientAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Consumer auditClientConsumer() {
        return new Consumer();
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Producer auditClientProducer() {
        return new Producer();
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuditClient asyncAuditClient(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        AsyncAuditClientImpl asyncAuditClient = new AsyncAuditClientImpl();
        asyncAuditClient.setAuditRest(auditRest);
        return asyncAuditClient;
    }
}
