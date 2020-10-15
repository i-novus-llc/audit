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

package ru.i_novus.ms.audit.client.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Тестирование класса AuditClientRequest
 *
 * @author akuznetcov
 **/
public class AuditClientRequestTest extends BaseAuditClientRequestTest {

    private static final String EVENT_TYPE_NEW = "EventTypeNew";
    private static final String OBJECT_TYPE_NEW = "ObjectTypeNew";
    private static final String OBJECT_ID_NEW = "ObjectIdNew";
    private static final String OBJECT_NAME_NEW = "ObjectNameNew";
    private static final String USER_ID_NEW = "UserIdNew";
    private static final String USERNAME_NEW = "usernameNew";
    private static final String SOURCE_WORKSTATION_NEW = "WorkstationNew";
    private static final String SOURCE_APPLICATION_NEW = "ApplicationNew";
    private static final String HOSTNAME_NEW = "HostnameNew";
    private static final String CONTEXT_NEW = "{\"field\": \"name\", \"value\": \"Значение_New\"}";
    private static final short AUDIT_TYPE_NEW = 2;
    private static final String SENDER_NEW = "SenderNew";
    private static final String RECEIVER_NEW = "ReceiverNew";

    private static final LocalDateTime EVENT_DATE = LocalDateTime.now().withNano(0);
    private static final String REQUEST_TO_STRING =
            "AuditClientRequest(eventDate=" + EVENT_DATE + ", auditType=1, context={\"field\": \"name\", \"value\": \"Значение\"}, eventType=AuditClientRequestParam{value='EventType', args=[]}, objectType=AuditClientRequestParam{value='ObjectType', args=[]}, objectId=AuditClientRequestParam{value='ObjectId', args=[]}, objectName=AuditClientRequestParam{value='ObjectName', args=[]}, userId=AuditClientRequestParam{value='UserId', args=[]}, username=AuditClientRequestParam{value='username', args=[]}, sourceWorkstation=AuditClientRequestParam{value='Workstation', args=[]}, sourceApplication=AuditClientRequestParam{value='Application', args=[]}, hostname=AuditClientRequestParam{value='Hostname', args=[]}, sender=AuditClientRequestParam{value='Sender', args=[]}, receiver=AuditClientRequestParam{value='Receiver', args=[]})";

    @Before
    public void beforeEach() {
        auditClientRequest.setEventDate(EVENT_DATE);
    }

    @Test
    public void auditClientRequestEventDateTest() {
        assertNotNull(auditClientRequest.getEventDate());
        assertEquals(EVENT_DATE, auditClientRequest.getEventDate());
    }

    @Test
    public void auditClientRequestToStringTest() {
        assertEquals(REQUEST_TO_STRING, auditClientRequest.toString());
    }

    @Test
    public void auditClientRequestEqualsHashTest() {
        AuditClientRequest request = createNewRequest();

        assertEquals(auditClientRequest, auditClientRequest);
        assertEquals(request, auditClientRequest);

        assertNotEquals(null, auditClientRequest);

        assertEquals(request.hashCode(), auditClientRequest.hashCode());
    }

    @Test
    public void auditClientRequestNotEqualsTest() {
        AuditClientRequest request = createNewRequest();

        assertNotEquals(null, auditClientRequest);

        request.setEventDate(LocalDateTime.now().withNano(0).minusMinutes(1));
        assertNotEquals(request, auditClientRequest);

        request.setEventDate(EVENT_DATE);
        request.setContext(CONTEXT_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setContext(CONTEXT);
        request.setAuditType(AUDIT_TYPE_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setAuditType(AUDIT_TYPE);
        request.setEventType(EVENT_TYPE_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setEventType(EVENT_TYPE);
        request.setHostname(HOSTNAME_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setHostname(HOSTNAME);
        request.setObjectId(OBJECT_ID_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setObjectId(OBJECT_ID);
        request.setObjectName(OBJECT_NAME_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setObjectName(OBJECT_NAME);
        request.setObjectType(OBJECT_TYPE_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setObjectType(OBJECT_TYPE);
        request.setReceiver(RECEIVER_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setReceiver(RECEIVER);
        request.setSender(SENDER_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setSender(SENDER);
        request.setSourceApplication(SOURCE_APPLICATION_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setSourceApplication(SOURCE_APPLICATION);
        request.setSourceWorkstation(SOURCE_WORKSTATION_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setSourceWorkstation(SOURCE_WORKSTATION);
        request.setUserId(USER_ID_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setUserId(USER_ID);
        request.setUsername(USERNAME_NEW);
        assertNotEquals(request, auditClientRequest);

        request.setUsername(USERNAME);
    }

    private AuditClientRequest createNewRequest() {
        AuditClientRequest request = new AuditClientRequest();
        request.setEventType(EVENT_TYPE);
        request.setObjectType(OBJECT_TYPE);
        request.setObjectId(OBJECT_ID);
        request.setObjectName(OBJECT_NAME);
        request.setUserId(USER_ID);
        request.setUsername(USERNAME);
        request.setSourceWorkstation(SOURCE_WORKSTATION);
        request.setSourceApplication(SOURCE_APPLICATION);
        request.setHostname(HOSTNAME);
        request.setContext(CONTEXT);
        request.setAuditType(AUDIT_TYPE);
        request.setSender(SENDER);
        request.setReceiver(RECEIVER);
        request.setEventDate(EVENT_DATE);

        return request;
    }
}
