package ru.i_novus.ms.audit.client.util.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class AuditMixIn {

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime eventDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime creationDate;
}
