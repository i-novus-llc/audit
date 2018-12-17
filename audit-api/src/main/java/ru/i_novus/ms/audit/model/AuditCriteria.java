package ru.i_novus.ms.audit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.n2oapp.platform.jaxrs.RestCriteria;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@ApiModel("Критерии поиска событий")
public class AuditCriteria extends RestCriteria {

    private static final int DAFAULT_PAGE = 1;
    private static final int DAFAULT_PAGE_SIZE = 10;
    private static final int MAX_SIZE = Integer.MAX_VALUE;

    @ApiModelProperty("Дата события (от)")
    @QueryParam("eventDateFrom")
    private LocalDateTime eventDateFrom;

    @ApiModelProperty("Дата события (до)")
    @QueryParam("eventDateTo")
    private LocalDateTime eventDateTo;

    @ApiModelProperty("Тип события")
    @QueryParam("eventType")
    private String eventType;

    @ApiModelProperty("Тип объекта")
    @QueryParam("objectType")
    private String objectType;

    @ApiModelProperty("Идентификатор объекта")
    @QueryParam("objectId")
    private String objectId;

    @ApiModelProperty("Наименование объекта")
    @QueryParam("objectName")
    private String objectName;

    @ApiModelProperty("Идентификатор пользователя")
    @QueryParam("userId")
    private String userId;

    @ApiModelProperty("Имя пользователя")
    @QueryParam("username")
    private String username;

    @ApiModelProperty("Имя программы")
    @QueryParam("sourceApplication")
    private String sourceApplication;

    @ApiModelProperty("Рабочая станция")
    @QueryParam("sourceWorkstation")
    private String sourceWorkstation;

    @ApiModelProperty("Контекст")
    @QueryParam("context")
    private String context;

    public AuditCriteria() {
        super(DAFAULT_PAGE, DAFAULT_PAGE_SIZE);
    }

    public void noPagination() {
        setPageSize(MAX_SIZE);
        setPageNumber(DAFAULT_PAGE);
    }

    public LocalDateTime getEventDateFrom() {
        return eventDateFrom;
    }

    public void setEventDateFrom(LocalDateTime eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public LocalDateTime getEventDateTo() {
        return eventDateTo;
    }

    public void setEventDateTo(LocalDateTime eventDateTo) {
        this.eventDateTo = eventDateTo;
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
