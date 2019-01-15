package ru.i_novus.ms.audit.client;

import ru.i_novus.ms.audit.client.model.AuditClientRequest;

public interface AuditClient {

    void add(AuditClientRequest request);
}