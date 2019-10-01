package ru.i_novus.ms.audit.client;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.i_novus.ms.audit.client.app.AuditClientApp;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuditClientApp.class)
public class AsyncAuditClientTest {

    private static AuditClientRequest auditClientRequest = new AuditClientRequest();

    @MockBean(name = "auditRestJaxRsProxyClient")
    private AuditRest auditRest;

    @Autowired
    private AuditClient asyncAuditClient;

    @Test
    @Ignore
    public void asyncAddTest() throws InterruptedException {
        asyncAuditClient.add(auditClientRequest);
        TimeUnit.SECONDS.sleep(3);
        verify(auditRest, times(1)).add(any());
    }
}