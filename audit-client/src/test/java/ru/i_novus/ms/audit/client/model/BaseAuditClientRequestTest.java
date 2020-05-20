package ru.i_novus.ms.audit.client.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author akuznetcov
 **/
public class BaseAuditClientRequestTest {

    protected static final String EVENT_TYPE = "EventType";
    protected static final String OBJECT_TYPE = "ObjectType";
    protected static final String OBJECT_ID = "ObjectId";
    protected static final String OBJECT_NAME = "ObjectName";
    protected static final String USER_ID = "UserId";
    protected static final String USER_ID_PARAMETRIZED = "Parametrized userId";
    protected static final String USERNAME = "username";
    protected static final String USERNAME_PARAMETRIZED = "Parametrized username";
    protected static final String SOURCE_WORKSTATION = "Workstation";
    protected static final String SOURCE_WORKSTATION_PARAMETRIZED = "Parametrized workstation";
    protected static final String SOURCE_APPLICATION = "Application";
    protected static final String SOURCE_APPLICATION_PARAMETRIZED = "Parametrized application";
    protected static final String HOSTNAME = "Hostname";
    protected static final String CONTEXT = "{\"field\": \"name\", \"value\": \"Значение\"}";
    protected static final short AUDIT_TYPE = 1;
    protected static final String SENDER = "Sender";
    protected static final String RECEIVER = "Receiver";
    protected static AuditClientRequest auditClientRequest;

    @Before
    public void init() {
        auditClientRequest = new AuditClientRequest();
        auditClientRequest.setEventType(EVENT_TYPE);
        auditClientRequest.setObjectType(OBJECT_TYPE);
        auditClientRequest.setObjectId(OBJECT_ID);
        auditClientRequest.setObjectName(OBJECT_NAME);
        auditClientRequest.setUserId(USER_ID);
        auditClientRequest.setUsername(USERNAME);
        auditClientRequest.setSourceWorkstation(SOURCE_WORKSTATION);
        auditClientRequest.setSourceApplication(SOURCE_APPLICATION);
        auditClientRequest.setHostname(HOSTNAME);
        auditClientRequest.setContext(CONTEXT);
        auditClientRequest.setAuditType(AUDIT_TYPE);
        auditClientRequest.setSender(SENDER);
        auditClientRequest.setReceiver(RECEIVER);
    }

    @Test
    public void auditClientRequestTest() {
        assertNotNull(auditClientRequest.getEventType());
        assertEquals(EVENT_TYPE, auditClientRequest.getEventType().getValue());

        assertNotNull(auditClientRequest.getObjectId());
        assertEquals(OBJECT_ID, auditClientRequest.getObjectId().getValue());

        assertNotNull(auditClientRequest.getObjectName());
        assertEquals(OBJECT_NAME, auditClientRequest.getObjectName().getValue());

        assertNotNull(auditClientRequest.getHostname());
        assertEquals(HOSTNAME, auditClientRequest.getHostname().getValue());

        assertNotNull(auditClientRequest.getReceiver());
        assertEquals(RECEIVER, auditClientRequest.getReceiver().getValue());

        assertNotNull(auditClientRequest.getSender());
        assertEquals(SENDER, auditClientRequest.getSender().getValue());

        assertNotNull(auditClientRequest.getSourceApplication());
        assertEquals(SOURCE_APPLICATION, auditClientRequest.getSourceApplication().getValue());

        assertNotNull(auditClientRequest.getSourceWorkstation());
        assertEquals(SOURCE_WORKSTATION, auditClientRequest.getSourceWorkstation().getValue());

        assertEquals(AUDIT_TYPE, (short) auditClientRequest.getAuditType());

        assertNotNull(auditClientRequest.getContext());
        assertEquals(CONTEXT, auditClientRequest.getContext());
    }

}
