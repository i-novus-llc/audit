package ru.i_novus.ms.audit.client;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.i_novus.ms.audit.client.app.AuditClientApp;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuditClientApp.class)
public class AsyncAuditClientTest {

    private static final String EVENT_TYPE = "EventType";
    private static final String OBJECT_TYPE = "ObjectType";
    private static final String OBJECT_ID = "ObjectId";
    private static final String OBJECT_NAME = "ObjectName";
    private static final String USER_ID = "785";
    private static final String USERNAME = "ekrasulina";
    private static final String SOURCE_WORKSTATION = "Workstation";
    private static final String SOURCE_APPLICATION = "Application";
    private static final String CONTEXT = "{\"field\": \"name\", \"value\": \"Значение\"}";
    private static final short AUDIT_TYPE = 1;
    private static final String SENDER = "Sender";
    private static final String RECEIVER = "Receiver";

    private static AuditClientRequest auditClientRequest = new AuditClientRequest();

    @MockBean(name = "auditRestJaxRsProxyClient")
    private AuditRest auditRest;

    @Autowired
    private AuditClient asyncAuditClient;

    @BeforeClass
    public static void initialize() {
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
        auditClientRequest.setContext(CONTEXT);
        auditClientRequest.setAuditType(AUDIT_TYPE);
        auditClientRequest.setSender(SENDER);
        auditClientRequest.setReceiver(RECEIVER);
    }

    @Test
    public void asyncAddTest() throws InterruptedException {
        asyncAuditClient.add(auditClientRequest);
        TimeUnit.SECONDS.sleep(3);
        verify(auditRest, times(1)).add(any());
    }
}