package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@ApiModel("Критерии поиска событий")
@Data
@AllArgsConstructor
@Getter
@Setter
public class AuditCriteriaDTO {

    @ApiModelProperty("Дата события (от)")
    @QueryParam("eventDateFrom")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDateFrom;

    @ApiModelProperty("Дата события (до)")
    @QueryParam("eventDateTo")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDateTo;

    @ApiModelProperty("Тип события")
    @QueryParam("eventType")
    private String eventType;

    @ApiModelProperty("Тип объекта")
    @QueryParam("auditObjectTypes")
    private String[] objectType;

    @ApiModelProperty("Идентификатор объекта")
    @QueryParam("auditObjectId")
    private String objectId;

    @ApiModelProperty("Наименование объекта")
    @QueryParam("auditObjectNames")
    private String[] objectName;

    @ApiModelProperty("Идентификатор пользователя")
    @QueryParam("userId")
    private String userId;

    @ApiModelProperty("Имя пользователя")
    @QueryParam("username")
    private String username;

    @ApiModelProperty("Имя программы")
    @QueryParam("auditSourceApplication")
    private String[] sourceApplication;

    @ApiModelProperty("Рабочая станция")
    @QueryParam("auditSourceWorkstations")
    private String sourceWorkstation;

    @ApiModelProperty("Контекст")
    @QueryParam("context")
    private String context;

    @ApiModelProperty("Имя хоста")
    @QueryParam("hostname")
    private String hostname;

    @ApiModelProperty("Страница")
    @QueryParam("page")
    private Integer page;

    @ApiModelProperty("Размер")
    @QueryParam("size")
    private Integer size;

}
