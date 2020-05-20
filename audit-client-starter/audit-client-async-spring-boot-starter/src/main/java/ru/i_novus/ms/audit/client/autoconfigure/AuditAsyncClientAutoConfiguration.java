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
