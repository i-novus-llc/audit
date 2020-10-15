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

package ru.i_novus.ms.audit.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.service.api.AuditRest;

public class SimpleAuditClientImpl extends AbstractAuditService {

    AuditRest auditRest;

    @Autowired
    public void setAuditRest(@Qualifier("auditRestJaxRsProxyClient") AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    @Override
    public void add(AuditClientRequest request) {
        auditRest.add(requestConverter.toAuditRequest(request));
    }
}