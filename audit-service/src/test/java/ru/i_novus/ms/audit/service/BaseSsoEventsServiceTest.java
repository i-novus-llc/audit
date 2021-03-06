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

package ru.i_novus.ms.audit.service;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaseSsoEventsServiceTest {
    private static final short AUDIT_TYPE_AUTHORIZATION = 3;

    private OpenIdProperties openIdProperties = new OpenIdProperties();
    @InjectMocks
    private BaseSsoEventsService ssoEventsService = new BaseSsoEventsService(openIdProperties);
    @Mock
    private AuditClient asyncAuditClient;
    @Mock
    private AuditService auditService;
    @Mock
    private OAuth2RestTemplate restTemplate;

    @Test
    public void testStartSynchronization() {
        ObjectMapper mapper = new ObjectMapper();

        // подготовка данных, которые mock будут возвращать при доступе к БД аудита
        Date lastEventDate = new Date();
        Date lastEventDate2 = new Date(lastEventDate.getTime() + 1000);
        LocalDateTime lastEventLocalDateTime = lastEventDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Audit lastAudit = new Audit();
        lastAudit.setEventDate(lastEventLocalDateTime);
        doReturn(lastAudit).when(auditService).getLastAudit(any(), any());

        doReturn(false).when(auditService).auditExists(any(), any(), any(), any(), any(), any());
        doReturn(true).when(auditService).auditExists(AUDIT_TYPE_AUTHORIZATION, lastEventLocalDateTime, "LOGIN", "user1", openIdProperties.getCode(), "{\"a\":\"A\"}");

        // подготовка данных, которые mock будет возвращать при доступе к REST сервису логов KeyCloak
        OpenIdEventLog eventLog1 = new OpenIdEventLog();
        eventLog1.setTime(lastEventDate);
        eventLog1.setType("LOGIN");
        eventLog1.setUserId("user1");
        eventLog1.setDetails(Map.of("a", "A"));

        OpenIdEventLog eventLog2 = new OpenIdEventLog();
        eventLog2.setTime(lastEventDate2);
        eventLog2.setType("LOGIN");
        eventLog2.setUserId("user1");
        eventLog2.setDetails(Map.of("a", "A"));

        OpenIdEventLog eventLog3 = new OpenIdEventLog();
        eventLog3.setTime(lastEventDate);
        eventLog3.setType("LOGIN_");
        eventLog3.setUserId("user1");
        eventLog3.setDetails(Map.of("a", "A"));

        OpenIdEventLog eventLog4 = new OpenIdEventLog();
        eventLog4.setTime(lastEventDate);
        eventLog4.setType("LOGIN");
        eventLog4.setUserId("user1_");
        eventLog4.setDetails(Map.of("a", "A"));

        OpenIdEventLog eventLog5 = new OpenIdEventLog();
        eventLog5.setTime(lastEventDate);
        eventLog5.setType("LOGIN");
        eventLog5.setUserId("user1");
        eventLog5.setDetails(Map.of("a", "B"));

        List<OpenIdEventLog> openIdEventLogs = List.of(eventLog1, eventLog2, eventLog3, eventLog4, eventLog5);

        doReturn(new ResponseEntity<>(openIdEventLogs, HttpStatus.OK), new ResponseEntity<List<OpenIdEventLog>>(List.of(), HttpStatus.OK))
                .when(restTemplate).exchange(any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<List<OpenIdEventLog>>>any(), any(Object[].class));

        // подготовка результата, который, как ожидается, будет направлен в asyncAuditClient
        ArrayList<AuditClientRequest> expected = new ArrayList<>();
        for (OpenIdEventLog event: openIdEventLogs) {
            //eventLog1 не должен попасть в asyncAuditClient, потому что он дублирует запись, уже существующую в БД логов
            if (event != eventLog1) {
                AuditClientRequest auditClientRequest = new AuditClientRequest();
                auditClientRequest.setEventDate(eventLog1.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
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

                expected.add(auditClientRequest);
            }
        }

        doAnswer(invocation -> {
            AuditClientRequest sent = invocation.getArgument(0);
            boolean found = false;
            for (AuditClientRequest exp: expected) {
                if (Objects.equals(exp.getEventDate(), sent.getEventDate()) &&
                        Objects.equals(exp.getEventDate(), sent.getEventDate()) &&
                        Objects.equals(exp.getAuditType(), sent.getAuditType()) &&
                        Objects.equals(exp.getSourceApplication().getValue(), sent.getSourceApplication().getValue()) &&
                        Objects.equals(exp.getUserId().getValue(), sent.getUserId().getValue()) &&
                        Objects.equals(exp.getEventType().getValue(), sent.getEventType().getValue()) &&
                        Objects.equals(exp.getEventDate(), sent.getEventDate()) &&
                        Objects.equals(exp.getSourceWorkstation().getValue(), sent.getSourceWorkstation().getValue()) &&
                        Objects.equals(exp.getUsername().getValue(), sent.getUsername().getValue()) &&
                        Objects.equals(exp.getContext(), sent.getContext())
                ) {
                    expected.remove(exp);
                    found = true;
                    break;
                }
            }
            assertTrue(found); // в asyncAuditClient попало событие, которое не должно было попадать
            return null;
        }).when(asyncAuditClient).add(any());

        ssoEventsService.startSynchronization();

        // какое-то из событий должно было попасть в asyncAuditClient, но не попало
        assertTrue(expected.isEmpty());
    }

}