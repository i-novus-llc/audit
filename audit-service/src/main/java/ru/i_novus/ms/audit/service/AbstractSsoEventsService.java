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
import org.springframework.web.client.HttpClientErrorException;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@EnableScheduling
@Slf4j
public abstract class AbstractSsoEventsService {
    @Autowired
    protected AuditClient asyncAuditClient;

    @Autowired
    protected AuditService auditService;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    protected OpenIdProperties openIdProperties;
    protected static final short AUDIT_TYPE_AUTHORIZATION = 3;
    protected static final int PAGE_SIZE = 100;

    AbstractSsoEventsService(OpenIdProperties openIdProperties) {
        this.openIdProperties = openIdProperties;
    }

    protected abstract void doSynchronization();

    protected abstract String getUrl(int pageNumber);

    @Scheduled(cron = "#{getScheduleCronSyntax}")
    public void startSynchronization() {
        doSynchronization();
    }

    protected List<OpenIdEventLog> getEvents(int pageNumber) {
        try {
            return doGetEvents(pageNumber);
        } catch (HttpClientErrorException.Unauthorized e) {
            log.info("Client unauthorized", e);
            //сброс access-токена для повторной авторизации
            restTemplate.getOAuth2ClientContext().setAccessToken(null);
            return doGetEvents(pageNumber);
        }
    }

    protected List<OpenIdEventLog> doGetEvents(int pageNumber) {
        String url = getUrl(pageNumber);

        ResponseEntity<List<OpenIdEventLog>> response =
                restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<OpenIdEventLog>>() {});
        return response.hasBody() ? response.getBody() : Collections.emptyList();
    }

    protected AuditClientRequest constructAuditClientRequest(OpenIdEventLog event, ObjectMapper mapper) {
        if (event.getDetails() == null) {
            event.setDetails(new HashMap<>());
        }

        if (StringUtils.isNotEmpty(event.getError())) {
            event.getDetails().put("error", event.getError());
        }
        if (StringUtils.isNotEmpty(event.getRealmId())) {
            event.getDetails().put("realmId", event.getRealmId());
        }
        if (StringUtils.isNotEmpty(event.getSessionId())) {
            event.getDetails().put("sessionId", event.getSessionId());
        }
        if (StringUtils.isNotEmpty(event.getClientId())) {
            event.getDetails().put("clientId", event.getClientId());
        }

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

        return auditClientRequest;
    }
}
