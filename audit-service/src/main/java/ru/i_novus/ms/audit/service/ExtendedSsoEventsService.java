/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.OpenIdProperties;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.OpenIdEventLog;

import java.time.ZoneId;
import java.util.List;

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
