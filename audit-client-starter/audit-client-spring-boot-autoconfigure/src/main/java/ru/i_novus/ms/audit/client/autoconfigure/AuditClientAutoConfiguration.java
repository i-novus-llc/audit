package ru.i_novus.ms.audit.client.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/META-INF/ru/i_novus/ms/audit/client/autoconfigure/default.properties")
public class AuditClientAutoConfiguration {
}
