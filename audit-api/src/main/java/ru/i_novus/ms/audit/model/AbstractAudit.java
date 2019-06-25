package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAudit implements Serializable {

    @ApiModelProperty(value = "Дата события", required = true)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    @ApiModelProperty(value = "Тип события", required = true)
    private String eventType;

    @ApiModelProperty(value = "Тип объекта", required = true)
    private String objectType;

    @ApiModelProperty(value = "Идентификатор объекта", required = true)
    private String objectId;

    @ApiModelProperty(value = "Наименование объекта", required = true)
    private String objectName;

    @ApiModelProperty(value = "Идентификатор пользователя", required = true)
    private String userId;

    @ApiModelProperty(value = "Имя пользователя", required = true)
    private String username;

    @ApiModelProperty(value = "Имя программы", required = true)
    private String sourceApplication;

    @ApiModelProperty(value = "Рабочая станция")
    private String sourceWorkstation;

    @ApiModelProperty(value = "Контекст", required = true, example = " \'{\'id\':4735,\'request_id\':5741,\'file_name\':\'protocol (2).zip\'," +
            "\'attachment_id\':\'df87052d-dc10-11e7-80b9-005056b1595b\',\'study_id\':null," +
            "\'created_on_base\':false}\' ")
    private String context;

    @ApiModelProperty(value = "Сервер")
    private String hostname;

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    public String getSourceWorkstation() {
        return sourceWorkstation;
    }

    public void setSourceWorkstation(String sourceWorkstation) {
        this.sourceWorkstation = sourceWorkstation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractAudit{" +
                "eventDate=" + eventDate +
                ", eventType='" + eventType + '\'' +
                ", auditObjectTypes='" + objectType + '\'' +
                ", auditObjectId='" + objectId + '\'' +
                ", auditObjectNames='" + objectName + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", auditSourceApplication='" + sourceApplication + '\'' +
                ", auditSourceWorkstations='" + sourceWorkstation + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
