package ru.i_novus.ms.audit.client.autoconfigure;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.filter.AuditClientFilter;
import ru.i_novus.ms.audit.client.impl.StubAuditClientImpl;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.util.List;

@Configuration
@EnableJaxRsProxyClient(classes = AuditRest.class, address = "${audit.service.url}")
public class AuditClientAutoConfiguration {

    @Value("${audit.client.sourceApplication:#{null}}")
    private String sourceApplication;

    @Value("${audit.client.filter-url-pattern:}")
    private List<String> filterUrlPattern;

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

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "filter-url-pattern")
    public FilterRegistrationBean<AuditClientFilter> auditClientFilter(@Autowired AuditClient auditClient) {
        FilterRegistrationBean<AuditClientFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuditClientFilter(auditClient));
        registrationBean.addUrlPatterns(filterUrlPattern.toArray(new String[0]));

        return registrationBean;
    }
}
