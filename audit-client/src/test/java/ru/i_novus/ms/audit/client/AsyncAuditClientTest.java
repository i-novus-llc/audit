package ru.i_novus.ms.audit.client;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.i_novus.ms.audit.client.app.AuditClientApp;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.service.api.AuditControllerApi;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuditClientApp.class)
@Import(AuditClientConfiguration.class)
public class AsyncAuditClientTest {

    private static AuditClientRequest auditClientRequest = new AuditClientRequest();

    @MockBean(name = "auditServiceJaxRsProxyClient")
    private AuditControllerApi auditControllerApi;

    @Autowired
    private AuditClient asyncAuditClient;

    @Test
    @Ignore
    public void asyncAddTest() throws InterruptedException {
//        asyncAuditClient.add(auditClientRequest);
        TimeUnit.SECONDS.sleep(3);
//        verify(auditControllerApi, times(1)).add(any());
    }
}