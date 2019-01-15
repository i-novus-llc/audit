package ru.i_novus.ms.audit.client;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.User;
import ru.i_novus.ms.audit.exception.AuditException;
import ru.i_novus.ms.audit.model.AuditRequest;

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

        AuditRequest auditRequest = requestConverter.toAuditRequest(auditClientRequest);

        assertEquals(auditRequest.getEventDate(), auditRequest.getEventDate());
        assertEquals(auditRequest.getEventType(), auditRequest.getEventType());
        assertEquals(auditRequest.getObjectType(), auditRequest.getObjectType());
        assertEquals(auditRequest.getObjectId(), auditRequest.getObjectId());
        assertEquals(auditRequest.getObjectName(), auditRequest.getObjectName());
        assertEquals(auditRequest.getUserId(), USER_ID);
        assertEquals(auditRequest.getUsername(), USERNAME);
        assertEquals(auditRequest.getSourceWorkstation(), SOURCE_WORKSTATION);
        assertEquals(auditRequest.getSourceApplication(), SOURCE_APPLICATION);
        assertEquals(auditRequest.getContext(), auditClientRequest.getContext());
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