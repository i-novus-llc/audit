package ru.i_novus.ms.audit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;

@ConfigurationProperties("export.query")
@Getter
public class QueryProperties {
    private int limitSelectRow = 0;
    private int hintFetchSize = 10;
}
