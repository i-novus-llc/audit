package ru.i_novus.ms.audit.model;

import java.util.HashMap;
import java.util.Map;

public class AuditTypeCode {
    public static final String ACTION_JOURNAL = "ACTION";
    public static final String INTEGRATION_JOURNAL = "INTEGRATION";
    public static final String AUTH_JOURNAL = "AUTH";

    private static Map<Short, String> codeAuditType = new HashMap<>();

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
