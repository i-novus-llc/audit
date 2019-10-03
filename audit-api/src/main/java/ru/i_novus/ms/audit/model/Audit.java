package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    /**
     * Constructor
     *
     * @param original parent object
     */
    public Audit(AbstractAudit original) {
        this.setAuditType(original.getAuditType());
        this.setContext(original.getContext());
        this.setEventDate(original.getEventDate());
        this.setHostname(original.getHostname());
        this.setEventType(original.getEventType());
        this.setObjectId(original.getObjectId());
        this.setObjectName(original.getObjectName());
        this.setObjectType(original.getObjectType());
        this.setReceiver(original.getReceiver());
        this.setSender(original.getSender());
        this.setSourceApplication(original.getSourceApplication());
        this.setSourceWorkstation(original.getSourceWorkstation());
        this.setUserId(original.getUserId());
        this.setUsername(original.getUsername());
    }
}
