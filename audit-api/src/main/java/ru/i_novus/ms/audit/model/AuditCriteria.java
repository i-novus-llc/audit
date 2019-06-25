package ru.i_novus.ms.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditCriteria extends RestCriteria {

    private static final int DAFAULT_PAGE = 1;
    private static final int DAFAULT_PAGE_SIZE = 10;
    private static final int MAX_SIZE = Integer.MAX_VALUE;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDateFrom;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDateTo;
    private String eventType;
    private String[] auditObjectTypes;
    private String objectId;
    private String[] auditObjectNames;
    private String userId;
    private String username;
    private String[] auditSourceApplications;
    private String auditSourceWorkstation;
    private String context;
    private String hostname;

    public AuditCriteria(AuditCriteriaDTO criteria) {
        this.context = criteria.getContext();
        this.eventDateFrom = criteria.getEventDateFrom();
        this.eventDateTo = criteria.getEventDateTo();
        this.eventType = criteria.getEventType();
        this.hostname = criteria.getHostname();
        this.objectId = criteria.getObjectId();
        this.auditObjectNames = criteria.getObjectName();
        this.auditObjectTypes = criteria.getObjectType();
        this.auditSourceApplications = criteria.getSourceApplication();
        this.auditSourceWorkstation = criteria.getSourceWorkstation();
        this.userId = criteria.getUserId();
        this.username = criteria.getUsername();
        this.setPageSize(criteria.getSize()==null ? DAFAULT_PAGE_SIZE : criteria.getSize());
        this.setPageNumber(criteria.getPage()==null ? DAFAULT_PAGE : criteria.getPage());
    }

    public void noPagination() {
        setPageSize(MAX_SIZE);
        setPageNumber(DAFAULT_PAGE);
    }


}
