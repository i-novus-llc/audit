package ru.i_novus.ms.audit.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.client.aspect.AuditAspect;
import ru.i_novus.ms.audit.client.security.properties.AuditProperties;


@Configuration
@ComponentScan("ru.i_novus.ms.audit.client.security")
@EnableConfigurationProperties({AuditProperties.class})
public class AuditClientUtilsConfiguration {

    @Bean
    public AuditAspect auditAspect(AuditProperties properties) {
        return new AuditAspect(properties);
    }
}