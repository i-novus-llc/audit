package ru.i_novus.ms.audit.web.controller;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.service.api.AuditRest;

/**
 * Контроллеры, используемые на страницах n2o.
 */
@Configuration
@EnableJaxRsProxyClient(classes = {AuditRest.class}, address = "${audit.backend.url}")
public class AuditN2oControllerConfiguration {
    @Bean
    public AuditN2oController auditReferenceFrontendController(@Qualifier("auditRestJaxRsProxyClient")AuditRest auditRest) {
        return new AuditN2oController(auditRest);
    }
}