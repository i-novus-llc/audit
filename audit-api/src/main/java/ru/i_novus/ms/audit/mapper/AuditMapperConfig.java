package ru.i_novus.ms.audit.mapper;

import net.n2oapp.platform.i18n.Messages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditMapperConfig {

    @Bean
    public AuditNotFoundExceptionMapper auditNotFoundExceptionMapper(Messages messages) {
        return new AuditNotFoundExceptionMapper(messages);
    }
}