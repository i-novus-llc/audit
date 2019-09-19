package ru.i_novus.ms.audit.client;

import net.n2oapp.platform.i18n.Messages;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.i_novus.ms.audit.client.app.TestedRequestConverter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RequestConverterTest {

    private static final String USER_ID = "785";
    private static final String USERNAME = "ekrasulina";
    private static final String SOURCE_APPLICATION = "app";
    private static final String SOURCE_WORKSTATION = "ws";

    private static AuditClientRequest auditClientRequest;

    private Messages messages = mock(Messages.class);

    @BeforeClass
    public static void initialize() {
        auditClientRequest = new AuditClientRequest();
        auditClientRequest.setEventDate(LocalDateTime.now().withNano(0));
        auditClientRequest.setEventType("EventType");
        auditClientRequest.setObjectType("ObjectType");
        auditClientRequest.setObjectId("ObjectId");
        auditClientRequest.setObjectName("ObjectName");
        auditClientRequest.setContext("{\"field\": \"name\", \"value\": \"Значение\"}");
        auditClientRequest.setAuditType((short) 1);
    }

    @Test
    public void successRequestConverterTest() {
        TestedRequestConverter requestConverter = new TestedRequestConverter(
                () -> new User(USER_ID, USERNAME),
                () -> SOURCE_APPLICATION,
                () -> SOURCE_WORKSTATION,
                messages
        );

        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditClientRequest.getEventDate(), auditForm.getEventDate());
        assertEquals(auditClientRequest.getEventType(), auditForm.getEventType());
        assertEquals(auditClientRequest.getObjectType(), auditForm.getObjectType());
        assertEquals(auditClientRequest.getObjectId(), auditForm.getObjectId());
        assertEquals(auditClientRequest.getObjectName(), auditForm.getObjectName());
        assertEquals(auditClientRequest.getContext(), auditForm.getContext());
        assertEquals(auditClientRequest.getEventType(), auditForm.getEventType());
        assertEquals(USER_ID, auditForm.getUserId());
        assertEquals(USERNAME, auditForm.getUsername());
        assertEquals(SOURCE_WORKSTATION, auditForm.getSourceWorkstation());
        assertEquals(SOURCE_APPLICATION, auditForm.getSourceApplication());
    }

    @Test(expected = AuditException.class)
    public void userAccessorUndefinedTest() {
        TestedRequestConverter requestConverter = new TestedRequestConverter(
                null,
                () -> null,
                () -> null,
                messages
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void userUndefinedTest() {
        TestedRequestConverter requestConverter = new TestedRequestConverter(
                () -> null,
                () -> null,
                () -> null,
                messages
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidUserIdTest() {
        TestedRequestConverter requestConverter = new TestedRequestConverter(
                () -> new User(null, USERNAME),
                () -> null,
                () -> null,
                messages
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidUsernameTest() {
        TestedRequestConverter requestConverter = new TestedRequestConverter(
                () -> new User(USER_ID, null),
                () -> null,
                () -> null,
                messages
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }
}