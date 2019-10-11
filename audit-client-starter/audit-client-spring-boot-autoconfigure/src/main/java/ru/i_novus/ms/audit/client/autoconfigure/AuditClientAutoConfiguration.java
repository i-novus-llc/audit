package ru.i_novus.ms.audit.client.autoconfigure;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.impl.StubAuditClientImpl;
import ru.i_novus.ms.audit.service.api.AuditRest;

@Configuration
@EnableJaxRsProxyClient(classes = AuditRest.class, address = "${audit.service.url}")
public class AuditClientAutoConfiguration {

    @Value("${audit.client.sourceApplication:#{null}}")
    private String sourceApplication;

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "sourceApplication")
    public SourceApplicationAccessor applicationAccessor() {
        return () -> sourceApplication;
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "false")
    public AuditClient stubAuditClient() {
        return new StubAuditClientImpl();
    }
}
