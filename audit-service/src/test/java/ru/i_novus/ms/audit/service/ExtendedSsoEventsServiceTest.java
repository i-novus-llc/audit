package ru.i_novus.ms.audit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExtendedSsoEventsServiceTest {
    private static final short AUDIT_TYPE_AUTHORIZATION = 3;

    private OpenIdProperties openIdProperties = new OpenIdProperties();
    @InjectMocks
    private ExtendedSsoEventsService ssoEventsService = new ExtendedSsoEventsService(openIdProperties);
    @Mock
    private AuditClient asyncAuditClient;
    @Mock
    private AuditService auditService;
    @Mock
    private OAuth2RestTemplate restTemplate;

    /*
     * Проверяем количество вызовов сервиса событий
     * При условии наличия 90 событий, учитывая размер пэйджа(default = 100), сервис должен вызваться 2 раза
     * первый раз получит 90, второй 0,
     * соотвественно 90 раз должен отработать метод asyncAuditClient.add для добавления события в аудит
     */
    @Test
    public void testStartSynchronizationCallRest() {
        int eventCount = 90;
        int callRestCount = 2;
        int callAsyncAuditClient = 90;

        doReturn(getLastAudit()).when(auditService).getLastAudit(any(), any());

        List<OpenIdEventLog> openIdEventLogs = getEventLogList(eventCount, new Date());

        doReturn(new ResponseEntity<>(openIdEventLogs, HttpStatus.OK), new ResponseEntity<List<OpenIdEventLog>>(List.of(), HttpStatus.OK))
                .when(restTemplate)
                .exchange(any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<OpenIdEventLog>>>any(), any(Object[].class));

        ssoEventsService.startSynchronization();

        verify(restTemplate, times(callRestCount))
                .exchange(any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<OpenIdEventLog>>>any(), any(Object[].class));

        verify(asyncAuditClient, times(callAsyncAuditClient)).add(any());
    }

    /*
     * Проверяем количество вызовов сервиса событий
     * При условии отуствия событий, сервис должен вызваться 1 раз
     * метод asyncAuditClient.add не должен вызываться
     */
    @Test
    public void testStartSynchronizationCallRestEmptyEventLogList() {
        int eventCount = 0;
        int callRestCount = 1;

        doReturn(getLastAudit()).when(auditService).getLastAudit(any(), any());

        List<OpenIdEventLog> openIdEventLogs = getEventLogList(eventCount, new Date());

        doReturn(new ResponseEntity<>(openIdEventLogs, HttpStatus.OK), new ResponseEntity<List<OpenIdEventLog>>(List.of(), HttpStatus.OK))
                .when(restTemplate)
                .exchange(any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<OpenIdEventLog>>>any(), any(Object[].class));

        ssoEventsService.startSynchronization();

        verify(restTemplate, times(callRestCount))
                .exchange(any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<OpenIdEventLog>>>any(), any(Object[].class));

        verify(asyncAuditClient, never()).add(any());
    }

    private Audit getLastAudit() {
        Date lastEventDate = new Date();
        LocalDateTime lastEventLocalDateTime = lastEventDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Audit lastAudit = new Audit();
        lastAudit.setEventDate(lastEventLocalDateTime);

        return lastAudit;
    }

    private List<OpenIdEventLog> getEventLogList(int count, Date startDt) {
        List<OpenIdEventLog> openIdEventLogs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            OpenIdEventLog eventLog = new OpenIdEventLog();
            eventLog.setTime(new Date(startDt.getTime() + i * 1000));
            eventLog.setType("LOGIN" + i);
            eventLog.setUserId("user" + i);
            eventLog.setDetails(Map.of("А", "У" + i));

            openIdEventLogs.add(eventLog);
        }

        return openIdEventLogs;
    }

}