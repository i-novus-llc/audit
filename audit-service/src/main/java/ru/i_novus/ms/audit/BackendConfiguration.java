package ru.i_novus.ms.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.provider.AuditParamConverterProvider;


@Configuration
public class BackendConfiguration {

    @Bean
    AuditParamConverterProvider auditParamConverterProvider() {
        return new AuditParamConverterProvider();
    }
}