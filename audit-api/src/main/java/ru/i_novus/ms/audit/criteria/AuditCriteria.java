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

package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Validated
public class AuditCriteria extends AuditRestCriteria {

    @QueryParam("id")
    private String id;

    @ApiParam(value = "Дата события (от)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateFrom")
    @NotNull
    private LocalDateTime eventDateFrom;

    @ApiParam(value = "Дата события (до)", format = "yyyy-MM-ddTHH:mm:ss")
    @QueryParam("eventDateTo")
    @NotNull
    private LocalDateTime eventDateTo;

    @ApiParam(value = "Объект")
    @QueryParam("objectType")
    private String[] objectType;

    @ApiParam(value = "Наименование объекта")
    @QueryParam("objectName")
    private String[] objectName;

    @ApiParam("Идентификатор объекта")
    @QueryParam("objectId")
    private String objectId;

    @ApiParam("Тип события")
    @QueryParam("eventType")
    private String[] auditEventType;

    @ApiParam("Идентификатор пользователя")
    @QueryParam("userId")
    private String userId;

    @ApiParam("Имя пользователя")
    @QueryParam("username")
    private String username;

    @ApiParam("Имя программы")
    @QueryParam("sourceApplication")
    private String[] sourceApplication;

    @ApiParam("Рабочая станция")
    @QueryParam("sourceWorkstation")
    private String sourceWorkstation;

    @ApiParam("Контекст")
    @QueryParam("context")
    private String context;

    @ApiParam("Имя хоста")
    @QueryParam("hostname")
    private String hostname;

    @ApiParam("Идентификатор типа журнала")
    @QueryParam("auditType")
    @NotNull
    private Short auditTypeId;

    @ApiParam("Код системы отправителя")
    @QueryParam("sender")
    private String[] sender;

    @ApiParam("Код системы получателя")
    @QueryParam("receiver")
    private String[] receiver;

    @SuppressWarnings("squid:S2637")
    public AuditCriteria() {
        defaultOrders.add(new Sort.Order(Sort.Direction.DESC, "eventDate"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditCriteria)) return false;
        if (!super.equals(o)) return false;
        AuditCriteria that = (AuditCriteria) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(eventDateFrom, that.eventDateFrom) &&
                Objects.equals(eventDateTo, that.eventDateTo) &&
                Arrays.equals(objectType, that.objectType) &&
                Arrays.equals(objectName, that.objectName) &&
                Objects.equals(objectId, that.objectId) &&
                Arrays.equals(auditEventType, that.auditEventType) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(username, that.username) &&
                Arrays.equals(sourceApplication, that.sourceApplication) &&
                Objects.equals(sourceWorkstation, that.sourceWorkstation) &&
                Objects.equals(context, that.context) &&
                Objects.equals(hostname, that.hostname) &&
                Objects.equals(auditTypeId, that.auditTypeId) &&
                Arrays.equals(sender, that.sender) &&
                Arrays.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), id, eventDateFrom, eventDateTo, objectId, userId, username, sourceWorkstation, context, hostname, auditTypeId);
        result = 31 * result + Arrays.hashCode(objectType);
        result = 31 * result + Arrays.hashCode(objectName);
        result = 31 * result + Arrays.hashCode(auditEventType);
        result = 31 * result + Arrays.hashCode(sourceApplication);
        result = 31 * result + Arrays.hashCode(sender);
        result = 31 * result + Arrays.hashCode(receiver);
        return result;
    }
}
