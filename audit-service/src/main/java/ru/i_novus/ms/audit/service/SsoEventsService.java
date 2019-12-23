package ru.i_novus.ms.audit.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@EnableScheduling
@Slf4j
public class SsoEventsService {
    @Autowired
    private AuditClient asyncAuditClient;

    @Autowired
    private AuditService auditService;

    @Autowired
    private OAuth2RestTemplate restTemplate;

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
        boolean isActual = true;
        while (isActual) {
            List<OpenIdEventLog> openIdEventLogs = getEvents(pageNumber);
            if (CollectionUtils.isEmpty(openIdEventLogs)) {
                break;
            }
            isActual = sendEvents(openIdEventLogs, lastEventDate);
            pageNumber++;
        }
    }

    private boolean sendEvents(List<OpenIdEventLog> openIdEventLogs, LocalDateTime lastEventDate) {

        if (!CollectionUtils.isEmpty(openIdEventLogs)) {
            ObjectMapper mapper = new ObjectMapper();

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
                    log.info("Context parsing error", e);
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

    private List<OpenIdEventLog> getEvents(int pageNumber) {
        try {
            return doGetEvents(pageNumber);
        } catch (HttpClientErrorException.Unauthorized e) {
            log.info("Client unauthorized", e);
            //сброс access-токена для повторной авторизации
            restTemplate.getOAuth2ClientContext().setAccessToken(null);
            return doGetEvents(pageNumber);
        }
    }

    private List<OpenIdEventLog> doGetEvents(int pageNumber) {
        String url = String.format("%s/events?first=%s&max=%s", openIdProperties.getEventsUrl(), pageNumber * PAGE_SIZE, PAGE_SIZE);
        ResponseEntity<List<OpenIdEventLog>> response = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<>() {
        });
        return response.hasBody() ? response.getBody() : Collections.emptyList();
    }
}
