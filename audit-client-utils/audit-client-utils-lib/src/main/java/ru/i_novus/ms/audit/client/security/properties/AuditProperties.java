package ru.i_novus.ms.audit.client.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("audit")
@Getter
@Setter
public class AuditProperties {
    Map<String, String> object;
}
