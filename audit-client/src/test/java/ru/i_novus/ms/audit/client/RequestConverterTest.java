package ru.i_novus.ms.audit.client;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditForm;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class RequestConverterTest {

    private static final String USER_ID = "785";
    private static final String USERNAME = "ekrasulina";
    private static final String SOURCE_APPLICATION = "app";
    private static final String SOURCE_WORKSTATION = "ws";

    private static AuditClientRequest auditClientRequest;

    @BeforeClass
    public static void initialize() {
        auditClientRequest = new AuditClientRequest();
        auditClientRequest.setEventDate(LocalDateTime.now().withNano(0));
        auditClientRequest.setEventType("EventType");
        auditClientRequest.setObjectType("ObjectType");
        auditClientRequest.setObjectId("ObjectId");
        auditClientRequest.setObjectName("ObjectName");
        auditClientRequest.setContext("{\"field\": \"name\", \"value\": \"Значение\"}");
    }

    @Test
    public void successRequestConverterTest() {
        RequestConverter requestConverter = new RequestConverter(
                () ->  new User(USER_ID, USERNAME),
                () -> SOURCE_APPLICATION,
                () -> SOURCE_WORKSTATION
        );

        AuditForm auditForm = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditForm.getEventDate(), auditForm.getEventDate());
        assertEquals(auditForm.getEventType(), auditForm.getEventType());
        assertEquals(auditForm.getObjectType(), auditForm.getObjectType());
        assertEquals(auditForm.getObjectId(), auditForm.getObjectId());
        assertEquals(auditForm.getObjectName(), auditForm.getObjectName());
        assertEquals(auditForm.getUserId(), USER_ID);
        assertEquals(auditForm.getUsername(), USERNAME);
        assertEquals(auditForm.getSourceWorkstation(), SOURCE_WORKSTATION);
        assertEquals(auditForm.getSourceApplication(), SOURCE_APPLICATION);
        assertEquals(auditForm.getContext(), auditClientRequest.getContext());
    }

    @Test(expected = AuditException.class)
    public void userAccessorUndefinedTest() {
        RequestConverter requestConverter = new RequestConverter(
                null,
                () -> null,
                () -> null
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void userUndefinedTest() {
        RequestConverter requestConverter = new RequestConverter(
                () -> null,
                () -> null,
                () -> null
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidUserIdTest() {
        RequestConverter requestConverter = new RequestConverter(
                () -> new User(null, USERNAME),
                () -> null,
                () -> null
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }

    @Test(expected = AuditException.class)
    public void invalidUsernameTest() {
        RequestConverter requestConverter = new RequestConverter(
                () -> new User(USER_ID, null),
                () -> null,
                () -> null
        );
        requestConverter.toAuditRequest(auditClientRequest);
    }
}