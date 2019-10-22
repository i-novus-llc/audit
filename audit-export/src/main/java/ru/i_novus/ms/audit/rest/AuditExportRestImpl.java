package ru.i_novus.ms.audit.rest;

import net.n2oapp.platform.i18n.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditTypeCode;
import ru.i_novus.ms.audit.service.AuditExportService;
import ru.i_novus.ms.audit.service.api.AuditExportRest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.time.Duration;

@Controller
public class AuditExportRestImpl implements AuditExportRest {

    @Context
    private HttpServletResponse httpServletResponse;

    @Autowired
    @Qualifier("CSVAuditExportServiceImpl")
    private AuditExportService auditExportService;

    @Override
    public void auditExport(AuditCriteria auditCriteria) throws IOException {
        if (auditCriteria.getAuditTypeId() == null
                || AuditTypeCode.getCodeAuditType(auditCriteria.getAuditTypeId()) == null) {
            throw new UserException("export.unknownJournalType");
        }

        long daysBetweenDates = Duration.between(auditCriteria.getEventDateFrom(), auditCriteria.getEventDateTo()).toDays();
        if (daysBetweenDates > 31) {
            throw new UserException("export.periodException");
        }

        auditExportService.getAuditStream(auditCriteria, httpServletResponse);
    }
}
