package ru.i_novus.ms.audit.client.impl;

import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;

/**
 * Класс-заглушка клиента для настройки audit.client.enabled=false
 */
public class StubAuditClientImpl implements AuditClient {

    @Override
    public void add(AuditClientRequest request) {
        //stub, do nothing
    }
}
