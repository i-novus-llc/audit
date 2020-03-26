package ru.i_novus.ms.audit.client.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Тестирование класса AuditClientRequest
 *
 * @author akuznetcov
 **/
public class AuditClientRequestTest extends BaseAuditClientRequestTest {

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
}
