package ru.i_novus.ms.audit.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.time.ZoneId;
import java.util.List;

@Service
public class ExtendedSsoEventsService extends AbstractSsoEventsService {

    private Long lastEventDateTime;

    public ExtendedSsoEventsService(OpenIdProperties openIdProperties) {
        super(openIdProperties);
    }

    public void doSynchronization() {
        Audit audit = auditService.getLastAudit(AUDIT_TYPE_AUTHORIZATION, openIdProperties.getCode());
        lastEventDateTime = audit == null ? null : audit.getEventDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        int pageNumber = 0;

        while (true) {
            List<OpenIdEventLog> openIdEventLogs = getEvents(pageNumber);

            if (CollectionUtils.isEmpty(openIdEventLogs)) {
                break;
            }

            sendEvents(openIdEventLogs);
            pageNumber++;
        }
    }

    private void sendEvents(List<OpenIdEventLog> openIdEventLogList) {
        ObjectMapper mapper = new ObjectMapper();

        for (OpenIdEventLog event : openIdEventLogList) {
            asyncAuditClient.add(constructAuditClientRequest(event, mapper));
        }
    }

    protected String getUrl(int pageNumber) {
        String url = String.format("%s/extended-events?first=%s&max=%s", openIdProperties.getEventsUrl(), pageNumber * PAGE_SIZE, PAGE_SIZE);

        if (lastEventDateTime != null) {
            url = String.format("%s&longDateTimeFrom=%s", url, lastEventDateTime);
        }

        return url;
    }
}
