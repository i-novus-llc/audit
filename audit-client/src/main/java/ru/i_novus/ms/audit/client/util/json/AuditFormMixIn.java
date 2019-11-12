package ru.i_novus.ms.audit.client.util.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class AuditFormMixIn {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime eventDate;
}
