package ru.i_novus.ms.audit.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("export")
@Getter
@Setter
public class FieldsProperties {
    Map<String, List<FieldMarker>> journals;
}
