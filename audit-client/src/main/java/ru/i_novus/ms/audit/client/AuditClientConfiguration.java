package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import ru.i_novus.ms.audit.client.impl.AsyncAuditClientImpl;
import ru.i_novus.ms.audit.client.impl.SimpleAuditClientImpl;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.service.api.AuditRest;

@EnableJms
@SpringBootConfiguration
@ComponentScan("ru.i_novus.ms.audit.client")
@EnableJaxRsProxyClient(classes = AuditRest.class, address = "${audit.client.apiUrl}")
public class AuditClientConfiguration {

    public static final String AUDIT_QUEUE = "audit.queue";

    @Value("${audit.client.userId}")
    private String userId;

    @Value("${audit.client.username}")
    private String username;

    @Value("${audit.client.sourceWorkstation:#{null}}")
    private String sourceWorkstation;

    @Value("${audit.client.sourceApplication:#{null}}")
    private String sourceApplication;

    @Bean
    public UserAccessor userAccessor() {
        return () -> new User(userId, username);
    }

    @Bean
    public SourceApplicationAccessor applicationAccessor() {
        return () -> sourceApplication;
    }

    @Bean
    public SourceWorkstationAccessor workstationAccessor() {
        return () -> sourceWorkstation;
    }

    @Bean
    public AuditClient asyncAuditClient(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        AsyncAuditClientImpl asyncAuditClient = new AsyncAuditClientImpl();
        asyncAuditClient.setAuditRest(auditRest);
        return asyncAuditClient;
    }

    @Bean
    public AuditClient simpleAuditClient(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        SimpleAuditClientImpl simpleAuditClient = new SimpleAuditClientImpl();
        simpleAuditClient.setAuditRest(auditRest);
        return simpleAuditClient;
    }
}