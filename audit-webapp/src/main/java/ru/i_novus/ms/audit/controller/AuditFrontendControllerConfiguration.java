package ru.i_novus.ms.audit.controller;

import net.n2oapp.platform.jaxrs.autoconfigure.EnableJaxRsProxyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.service.api.AuditReferenceRest;

/**
 * Контроллеры, используемые на страницах n2o.
 */
@Configuration
@EnableJaxRsProxyClient(classes = {AuditReferenceRest.class}, address = "${audit.backend.url}")
public class AuditFrontendControllerConfiguration {
    @Bean
    public AuditReferenceFrontendController auditReferenceFrontendController(@Qualifier("auditReferenceRestJaxRsProxyClient") AuditReferenceRest auditReferenceRest) {
        return new AuditReferenceFrontendController(auditReferenceRest);
    }
}