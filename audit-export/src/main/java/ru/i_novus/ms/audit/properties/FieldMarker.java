package ru.i_novus.ms.audit.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldMarker {
    private String name;
    private String caption;
    private int order;
}
