package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.i18n.Messages;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.i_novus.ms.audit.client.app.TestedRequestConverter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RequestConverterTest {

    private static final String EVENT_TYPE = "EventType";
    private static final String OBJECT_TYPE = "ObjectType";
    private static final String OBJECT_ID = "ObjectId";
    private static final String OBJECT_NAME = "ObjectName";
    private static final String USER_ID = "785";
    private static final String USERNAME = "ekrasulina";
    private static final String SOURCE_WORKSTATION = "Workstation";
    private static final String SOURCE_APPLICATION = "Application";
    private static final String SOURCE_APPLICATION_PARAMETRIZED = "Parametrized application";
    private static final String CONTEXT = "{\"field\": \"name\", \"value\": \"Значение\"}";
    private static final short AUDIT_TYPE = 1;
    private static final String SENDER = "Sender";
    private static final String RECEIVER = "Receiver";

    private static TestedRequestConverter requestConverter;

    private static AuditClientRequest auditClientRequest;

    private static Messages messages = mock(Messages.class);

    @BeforeClass
    public static void initialize() {
        requestConverter = new TestedRequestConverter(
                () -> SOURCE_APPLICATION,
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
        auditClientRequest.setSourceApplication(SOURCE_APPLICATION_PARAMETRIZED);
        auditClientRequest.setContext(CONTEXT);
        auditClientRequest.setAuditType(AUDIT_TYPE);
        auditClientRequest.setSender(SENDER);
        auditClientRequest.setReceiver(RECEIVER);
    }

    @Test
    public void successRequestConverterTest() {
        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditClientRequest.getEventDate(), auditForm.getEventDate());
        assertEquals(auditClientRequest.getEventType(), auditForm.getEventType());
        assertEquals(auditClientRequest.getObjectType(), auditForm.getObjectType());
        assertEquals(auditClientRequest.getObjectId(), auditForm.getObjectId());
        assertEquals(auditClientRequest.getObjectName(), auditForm.getObjectName());
        assertEquals(auditClientRequest.getUserId(), auditForm.getUserId());
        assertEquals(auditClientRequest.getUsername(), auditForm.getUsername());
        assertEquals(auditClientRequest.getSourceWorkstation(), auditForm.getSourceWorkstation());
        assertEquals(auditClientRequest.getSourceApplication(), auditForm.getSourceApplication());
        assertEquals(auditClientRequest.getContext(), auditForm.getContext());
        assertEquals(auditClientRequest.getAuditType(), auditForm.getAuditType());
        assertEquals(auditClientRequest.getSender(), auditForm.getSender());
        assertEquals(auditClientRequest.getReceiver(), auditForm.getReceiver());
    }

    @Test
    public void successRequestConverterTestParametrized() {
        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        auditClientRequest.setSourceApplication(null);

        assertEquals(auditClientRequest.getEventDate(), auditForm.getEventDate());
        assertEquals(auditClientRequest.getEventType(), auditForm.getEventType());
        assertEquals(auditClientRequest.getObjectType(), auditForm.getObjectType());
        assertEquals(auditClientRequest.getObjectId(), auditForm.getObjectId());
        assertEquals(auditClientRequest.getObjectName(), auditForm.getObjectName());
        assertEquals(auditClientRequest.getUserId(), auditForm.getUserId());
        assertEquals(auditClientRequest.getUsername(), auditForm.getUsername());
        assertEquals(auditClientRequest.getSourceWorkstation(), auditForm.getSourceWorkstation());
        assertEquals(SOURCE_APPLICATION_PARAMETRIZED, auditForm.getSourceApplication());
        assertEquals(auditClientRequest.getContext(), auditForm.getContext());
        assertEquals(auditClientRequest.getAuditType(), auditForm.getAuditType());
        assertEquals(auditClientRequest.getSender(), auditForm.getSender());
        assertEquals(auditClientRequest.getReceiver(), auditForm.getReceiver());
    }

    @Test(expected = AuditException.class)
    public void invalidEventTypeTest() {
        auditClientRequest.setEventType(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidContextTest() {
        auditClientRequest.setContext(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidAuditTypeTest() {
        auditClientRequest.setAuditType(null);

        requestConverter.toAuditRequest(auditClientRequest);
    }
}