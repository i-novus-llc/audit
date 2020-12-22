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

package ru.i_novus.ms.audit.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.service.api.AuditRest;

import java.util.UUID;

/**
 * Контроллер данных аудита для страниц n2o
 */
@Slf4j
public class AuditN2oController {
    private AuditRest auditRest;

    public AuditN2oController(AuditRest auditRest) {
        this.auditRest = auditRest;
    }

    public Audit getAudit(UUID id) {
        Audit audit = auditRest.getById(id);
        ObjectMapper mapper = new ObjectMapper();
        String ctx;
        try {
            JsonNode json = mapper.readTree(audit.getContext());
            JsonNode requestHeader = ((ObjectNode) json).get("RequestHeaders");

            if (requestHeader != null) {
                ((ObjectNode) requestHeader).put("cookie", "");
            }

            ctx = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            log.info("Context parsing error", e);
            ctx = audit.getContext();
        }
        audit.setContext(ctx);
        return audit;
    }
}
