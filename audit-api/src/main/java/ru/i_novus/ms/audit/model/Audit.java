package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.UUID;

@ApiModel("Событие")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit extends AbstractAudit {

    @ApiModelProperty("Идентификатор события")
    private UUID id;

    @ApiModelProperty("Дата создания")
    private LocalDateTime creationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
