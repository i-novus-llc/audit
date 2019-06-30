package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@ApiModel("Событие")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Audit extends AbstractAudit {

    @ApiModelProperty("Идентификатор события")
    private UUID id;

    @ApiModelProperty("Дата создания")
    private LocalDateTime creationDate;

    @Builder
    public Audit (UUID id, LocalDateTime creationDate, LocalDateTime eventDate, String eventType, String objectType,
                  String objectId, String objectName, String userId, String username, String sourceApplication,
                  String sourceWorkstation, String context, String hostname){

        super(eventDate, eventType, objectType, objectId, objectName,
                userId, username, sourceApplication, sourceWorkstation, context, hostname);

        this.creationDate = creationDate;
        this.id = id;
    }
}
