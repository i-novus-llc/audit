package ru.i_novus.ms.audit.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("export.csv")
@Getter
@Setter
public class CSVProperties {
    private String fieldDelimiter = ",";
    private boolean printFieldName = true;
}
