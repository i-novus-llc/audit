package ru.i_novus.ms.audit.client.app;

import org.springframework.beans.factory.annotation.Autowired;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;

public class AuditClientRest {

    @Autowired
    private AuditClient asyncAuditClient;

    @Autowired
    private AuditClient simpleAuditClient;

    public void asyncAdd(AuditClientRequest request) {
        asyncAuditClient.add(request);
    }

    public void simpleAdd(AuditClientRequest request) {
        simpleAuditClient.add(request);
    }

}
