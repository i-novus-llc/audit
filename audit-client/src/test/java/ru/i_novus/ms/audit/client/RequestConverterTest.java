package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.i18n.Messages;
import net.n2oapp.platform.i18n.UserException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.i_novus.ms.audit.client.app.TestedRequestConverter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestConverterTest {

    private static final String EVENT_TYPE = "EventType";
    private static final String OBJECT_TYPE = "ObjectType";
    private static final String OBJECT_ID = "ObjectId";
    private static final String OBJECT_NAME = "ObjectName";
    private static final String USER_ID = "UserId";
    private static final String USER_ID_PARAMETRIZED = "Parametrized userId";
    private static final String USERNAME = "username";
    private static final String USERNAME_PARAMETRIZED = "Parametrized username";
    private static final String SOURCE_WORKSTATION = "Workstation";
    private static final String SOURCE_WORKSTATION_PARAMETRIZED = "Parametrized workstation";
    private static final String SOURCE_APPLICATION = "Application";
    private static final String SOURCE_APPLICATION_PARAMETRIZED = "Parametrized application";
    private static final String HOSTNAME = "Hostname";
    private static final String CONTEXT = "{\"field\": \"name\", \"value\": \"Значение\"}";
    private static final short AUDIT_TYPE = 1;
    private static final String SENDER = "Sender";
    private static final String RECEIVER = "Receiver";

    private static TestedRequestConverter requestConverter;

    private static AuditClientRequest auditClientRequest;

    private static Messages messages = mock(Messages.class);

    @BeforeClass
    public static void initialize() {
        when(messages.getMessage(EVENT_TYPE)).thenReturn(EVENT_TYPE);
        when(messages.getMessage(OBJECT_TYPE)).thenReturn(OBJECT_TYPE);
        when(messages.getMessage(OBJECT_ID)).thenReturn(OBJECT_ID);
        when(messages.getMessage(OBJECT_NAME)).thenReturn(OBJECT_NAME);
        when(messages.getMessage(USER_ID)).thenReturn(USER_ID);
        when(messages.getMessage(USER_ID_PARAMETRIZED)).thenReturn(USER_ID_PARAMETRIZED);
        when(messages.getMessage(USERNAME)).thenReturn(USERNAME);
        when(messages.getMessage(USERNAME_PARAMETRIZED)).thenReturn(USERNAME_PARAMETRIZED);
        when(messages.getMessage(SOURCE_WORKSTATION)).thenReturn(SOURCE_WORKSTATION);
        when(messages.getMessage(SOURCE_WORKSTATION_PARAMETRIZED)).thenReturn(SOURCE_WORKSTATION_PARAMETRIZED);
        when(messages.getMessage(SOURCE_APPLICATION)).thenReturn(SOURCE_APPLICATION);
        when(messages.getMessage(SOURCE_APPLICATION_PARAMETRIZED)).thenReturn(SOURCE_APPLICATION_PARAMETRIZED);
        when(messages.getMessage(HOSTNAME)).thenReturn(HOSTNAME);
        when(messages.getMessage(SENDER)).thenReturn(SENDER);
        when(messages.getMessage(RECEIVER)).thenReturn(RECEIVER);

        requestConverter = new TestedRequestConverter(
                () -> new User(USER_ID_PARAMETRIZED, USERNAME_PARAMETRIZED),
                () -> SOURCE_WORKSTATION_PARAMETRIZED,
                () -> SOURCE_APPLICATION_PARAMETRIZED,
                messages
        );
    }

    @Before
    public void beforeEach() {
        auditClientRequest = new AuditClientRequest();
        auditClientRequest.setEventDate(LocalDateTime.now().withNano(0));
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
    public void successRequestConverterTest() {
        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditClientRequest.getEventDate(), auditForm.getEventDate());
        assertEquals(auditClientRequest.getEventType().getValue(), auditForm.getEventType());
        assertEquals(auditClientRequest.getObjectType().getValue(), auditForm.getObjectType());
        assertEquals(auditClientRequest.getObjectId().getValue(), auditForm.getObjectId());
        assertEquals(auditClientRequest.getObjectName().getValue(), auditForm.getObjectName());
        assertEquals(auditClientRequest.getUserId().getValue(), auditForm.getUserId());
        assertEquals(auditClientRequest.getUsername().getValue(), auditForm.getUsername());
        assertEquals(auditClientRequest.getSourceWorkstation().getValue(), auditForm.getSourceWorkstation());
        assertEquals(auditClientRequest.getSourceApplication().getValue(), auditForm.getSourceApplication());
        assertEquals(auditClientRequest.getHostname().getValue(), auditForm.getHostname());
        assertEquals(auditClientRequest.getContext(), auditForm.getContext());
        assertEquals(auditClientRequest.getAuditType(), auditForm.getAuditType());
        assertEquals(auditClientRequest.getSender().getValue(), auditForm.getSender());
        assertEquals(auditClientRequest.getReceiver().getValue(), auditForm.getReceiver());
    }

    @Test
    public void successRequestConverterTestParametrized() {
        auditClientRequest.setUserId(null);
        auditClientRequest.setUsername(null);
        auditClientRequest.setSourceWorkstation(null);
        auditClientRequest.setSourceApplication(null);

        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditClientRequest.getEventDate(), auditForm.getEventDate());
        assertEquals(auditClientRequest.getEventType().getValue(), auditForm.getEventType());
        assertEquals(auditClientRequest.getObjectType().getValue(), auditForm.getObjectType());
        assertEquals(auditClientRequest.getObjectId().getValue(), auditForm.getObjectId());
        assertEquals(auditClientRequest.getObjectName().getValue(), auditForm.getObjectName());
        assertEquals(USER_ID_PARAMETRIZED, auditForm.getUserId());
        assertEquals(USERNAME_PARAMETRIZED, auditForm.getUsername());
        assertEquals(SOURCE_WORKSTATION_PARAMETRIZED, auditForm.getSourceWorkstation());
        assertEquals(SOURCE_APPLICATION_PARAMETRIZED, auditForm.getSourceApplication());
        assertEquals(auditClientRequest.getHostname().getValue(), auditForm.getHostname());
        assertEquals(auditClientRequest.getContext(), auditForm.getContext());
        assertEquals(auditClientRequest.getAuditType(), auditForm.getAuditType());
        assertEquals(auditClientRequest.getSender().getValue(), auditForm.getSender());
        assertEquals(auditClientRequest.getReceiver().getValue(), auditForm.getReceiver());
    }

    @Test(expected = UserException.class)
    public void invalidEventTypeTest() {
        auditClientRequest.setEventType(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = UserException.class)
    public void invalidContextTest() {
        auditClientRequest.setContext(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = UserException.class)
    public void invalidAuditTypeTest() {
        auditClientRequest.setAuditType(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }
}