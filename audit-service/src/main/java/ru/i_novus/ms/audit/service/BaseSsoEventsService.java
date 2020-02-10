package ru.i_novus.ms.audit.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BaseSsoEventsService extends AbstractSsoEventsService {

    public BaseSsoEventsService(OpenIdProperties openIdProperties) {
        super(openIdProperties);
    }

    public void doSynchronization() {
        Audit audit = auditService.getLastAudit(AUDIT_TYPE_AUTHORIZATION, openIdProperties.getCode());
        LocalDateTime lastEventDate = audit == null ? null : audit.getEventDate();
        int pageNumber = 0;
        boolean isActual = true;
        while (isActual) {
            List<OpenIdEventLog> openIdEventLogs = getEvents(pageNumber);
            if (CollectionUtils.isEmpty(openIdEventLogs)) {
                break;
            }
            isActual = sendEvents(openIdEventLogs, lastEventDate);
            pageNumber++;
        }
    }

    private boolean sendEvents(List<OpenIdEventLog> openIdEventLogs, LocalDateTime lastEventDate) {
        if (!CollectionUtils.isEmpty(openIdEventLogs)) {
            ObjectMapper mapper = new ObjectMapper();

            for (OpenIdEventLog event : openIdEventLogs) {
                if (lastEventDate != null
                        && lastEventDate.isAfter(event.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                    return false;
                }

                AuditClientRequest auditClientRequest = constructAuditClientRequest(event, mapper);

                if (lastEventDate == null ||
                        !auditService.auditExists(AUDIT_TYPE_AUTHORIZATION, auditClientRequest.getEventDate(), auditClientRequest.getEventType().getValue(),
                                auditClientRequest.getUserId().getValue(), auditClientRequest.getSourceApplication().getValue(), auditClientRequest.getContext())) {
                    asyncAuditClient.add(auditClientRequest);
                }
            }
        }
        return true;
    }

    protected String getUrl(int pageNumber) {
        return String.format("%s/events?first=%s&max=%s", openIdProperties.getEventsUrl(), pageNumber * PAGE_SIZE, PAGE_SIZE);
    }
}
