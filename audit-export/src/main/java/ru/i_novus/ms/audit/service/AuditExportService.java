package ru.i_novus.ms.audit.service;

import ru.i_novus.ms.audit.criteria.AuditCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuditExportService {
    void getAuditStream(AuditCriteria auditCriteria, HttpServletResponse httpServletResponse) throws IOException;
}
