package ru.i_novus.ms.audit.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("export.config")
@Getter
@Setter
public class ConfigProperties {
    private String ifNullValue = "";
    private String dateFormat = "dd.MM.yyyy HH:mm";
    private String fileName = "audit_export";
}
