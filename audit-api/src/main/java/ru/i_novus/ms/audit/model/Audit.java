/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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
