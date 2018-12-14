package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public abstract class AbstractAudit {

    @ApiModelProperty("Дата события")
    private LocalDateTime eventDate;

    @ApiModelProperty("Тип события")
    private String eventType;

    @ApiModelProperty("Тип объекта")
    private String objectType;

    @ApiModelProperty("Идентификатор объекта")
    private String objectId;

    @ApiModelProperty("Наименование объекта")
    private String objectName;

    @ApiModelProperty("Идентификатор пользователя")
    private String userId;

    @ApiModelProperty("Имя пользователя")
    private String username;

    @ApiModelProperty("Имя программы")
    private String sourceApplication;

    @ApiModelProperty("Рабочая станция")
    private String sourceWorkstation;

    @ApiModelProperty("Контекст")
    private String context;

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
}
