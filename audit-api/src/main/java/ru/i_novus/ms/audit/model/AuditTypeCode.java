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

package ru.i_novus.ms.audit.model;

import java.util.HashMap;
import java.util.Map;

public class AuditTypeCode {
    public static final String ACTION_JOURNAL = "ACTION";
    public static final String INTEGRATION_JOURNAL = "INTEGRATION";
    public static final String AUTH_JOURNAL = "AUTH";

    private static final Map<Short, String> codeAuditType = new HashMap<>();

    private AuditTypeCode() {
    }

    static {
        codeAuditType.put((short) 1, ACTION_JOURNAL);
        codeAuditType.put((short) 2, INTEGRATION_JOURNAL);
        codeAuditType.put((short) 3, AUTH_JOURNAL);
    }

    public static String getCodeAuditType(Short auditTypeId) {
        return codeAuditType.get(auditTypeId);
    }
}
