package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("Запрос на добавление события")
@Getter
@NoArgsConstructor
public class AuditForm extends AbstractAudit {

    @JsonCreator
    public AuditForm(@JsonProperty(required = true, value = "eventDate") LocalDateTime eventDate,
                     @JsonProperty(required = true, value = "eventType") String eventType,
                     @JsonProperty(required = true, value = "auditObjectTypes") String objectType,
                     @JsonProperty(required = true, value = "auditObjectId") String objectId,
                     @JsonProperty(required = true, value = "auditObjectNames") String objectName,
                     @JsonProperty(required = true, value = "userId") String userId,
                     @JsonProperty(required = true, value = "username") String username,
                     @JsonProperty(required = true, value = "auditSourceApplication") String sourceApplication,
                     @JsonProperty(required = true, value = "context") String context,
                     @JsonProperty("auditSourceWorkstations") String sourceWorkstation,
                     @JsonProperty("hostname") String hostname) {

        super(eventDate, eventType, objectType, objectId, objectName, userId, username,
                sourceApplication, sourceWorkstation, context, hostname);
    }
}
