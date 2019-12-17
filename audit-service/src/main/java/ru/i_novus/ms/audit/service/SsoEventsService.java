package ru.i_novus.ms.audit.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.criteria.OpenIdEventLogCriteria;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;
import ru.i_novus.ms.audit.service.api.OpenIdEventLogRest;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class SsoEventsService {
    @Autowired
    private AuditClient asyncAuditClient;

    @Autowired
    private AuditService auditService;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private OpenIdEventLogRest openIdEventLogRest;

    private OpenIdProperties openIdProperties;
    private static final short AUDIT_TYPE_AUTHORIZATION = 3;
    private static final int PAGE_SIZE = 100;

    public SsoEventsService(OpenIdProperties openIdProperties) {
        this.openIdProperties = openIdProperties;
    }

    @Scheduled(cron = "#{getScheduleCronSyntax}")
    public void startSynchronization() {
        Audit audit = auditService.getLastAudit(AUDIT_TYPE_AUTHORIZATION, openIdProperties.getCode());
        LocalDateTime lastEventDate = audit == null ? null : audit.getEventDate();
        int pageNumber = 0;
        OpenIdEventLogCriteria criteria = OpenIdEventLogCriteria.builder()
                .dateFrom(lastEventDate)
                .first(pageNumber)
                .max(PAGE_SIZE)
                .build();

        try {
            WebClient.client(openIdEventLogRest)
                    .accept(MediaType.APPLICATION_JSON)
                    .authorization(
                            String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, restTemplate.getAccessToken().getValue()));
        } catch (Exception e) {
            log.error(String.format("SSO Server %s authorization error!", openIdProperties.getAuthServerUri()), e);
            return;
        }

        boolean isActual = true;
        while (isActual) {
            List<OpenIdEventLog> openIdEventLogs = openIdEventLogRest.get(criteria);

            if (CollectionUtils.isEmpty(openIdEventLogs)) {
                break;
            }
            isActual = sendEvents(openIdEventLogs, lastEventDate);
            pageNumber++;
            criteria.setFirst(pageNumber * PAGE_SIZE);
        }
    }

    private boolean sendEvents(List<OpenIdEventLog> openIdEventLogs, LocalDateTime lastEventDate) {
        ObjectMapper mapper = new ObjectMapper();

        if (!CollectionUtils.isEmpty(openIdEventLogs)) {
            for (OpenIdEventLog event : openIdEventLogs) {
                if (lastEventDate != null
                        && lastEventDate.isAfter(event.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                    return false;
                }
                constructEventDetails(event);
                AuditClientRequest auditClientRequest = new AuditClientRequest();
                auditClientRequest.setAuditType(AUDIT_TYPE_AUTHORIZATION);
                auditClientRequest.setSourceApplication(openIdProperties.getCode());
                auditClientRequest.setUserId(event.getUserId());
                auditClientRequest.setEventType(event.getType());
                auditClientRequest.setEventDate(event.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                auditClientRequest.setSourceWorkstation(event.getIpAddress());
                auditClientRequest.setUsername(event.getDetails().getOrDefault("username", null));

                String context;
                try {
                    context = mapper.writeValueAsString(event.getDetails());
                } catch (IOException e) {
                    context = StringUtils.join(event.getDetails());
                }
                auditClientRequest.setContext(context);

                if (lastEventDate == null ||
                        !auditService.auditExists(AUDIT_TYPE_AUTHORIZATION, auditClientRequest.getEventDate(), auditClientRequest.getEventType().getValue(),
                                auditClientRequest.getUserId().getValue(), auditClientRequest.getSourceApplication().getValue(), auditClientRequest.getContext())) {
                    asyncAuditClient.add(auditClientRequest);
                }
            }
        }
        return true;
    }

    private void constructEventDetails(OpenIdEventLog event) {
        if (event.getDetails() == null) {
            event.setDetails(new HashMap<>());
        }

        if (!StringUtils.isEmpty(event.getError())) {
            event.getDetails().put("error", event.getError());
        }
        if (!StringUtils.isEmpty(event.getRealmId())) {
            event.getDetails().put("realmId", event.getRealmId());
        }
        if (!StringUtils.isEmpty(event.getSessionId())) {
            event.getDetails().put("sessionId", event.getSessionId());
        }
        if (!StringUtils.isEmpty(event.getClientId())) {
            event.getDetails().put("clientId", event.getClientId());
        }
    }
}
