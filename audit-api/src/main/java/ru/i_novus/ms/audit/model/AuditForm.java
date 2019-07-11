package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel("Запрос на добавление события")
@Getter
@Setter
@NoArgsConstructor
public class AuditForm extends AbstractAudit {

    @JsonCreator
    public AuditForm(@JsonProperty(required = true, value = "eventDate") LocalDateTime eventDate,
                     @JsonProperty(required = true, value = "eventType") String eventType,
                     @JsonProperty(required = true, value = "objectType") String objectType,
                     @JsonProperty(required = true, value = "objectId") String objectId,
                     @JsonProperty(required = true, value = "objectName") String objectName,
                     @JsonProperty(required = true, value = "userId") String userId,
                     @JsonProperty(required = true, value = "username") String username,
                     @JsonProperty(required = true, value = "sourceApplication") String sourceApplication,
                     @JsonProperty(required = true, value = "context") String context,
                     @JsonProperty("sourceWorkstation") String sourceWorkstation,
                     @JsonProperty("hostname") String hostname) {

        super(eventDate, eventType, objectType, objectId, objectName, userId, username,
                sourceApplication, sourceWorkstation, context, hostname);
    }
}
