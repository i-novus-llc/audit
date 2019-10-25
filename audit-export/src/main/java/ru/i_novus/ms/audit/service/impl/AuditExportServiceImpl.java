package ru.i_novus.ms.audit.service.impl;

import com.google.common.net.HttpHeaders;
import net.n2oapp.platform.i18n.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.i_novus.ms.audit.model.AuditTypeCode;
import ru.i_novus.ms.audit.properties.ConfigProperties;
import ru.i_novus.ms.audit.properties.FieldMarker;
import ru.i_novus.ms.audit.properties.FieldsProperties;
import ru.i_novus.ms.audit.criteria.AuditCriteria;

import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.repository.AuditExportRepository;
import ru.i_novus.ms.audit.service.AuditExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
* Абстрактный класс для реализаций экспорта в различные форматы
* */
@Service
@EnableConfigurationProperties({FieldsProperties.class, ConfigProperties.class})
abstract class AuditExportServiceImpl implements AuditExportService {

    @Autowired
    private AuditExportRepository auditExportRepository;

    @Autowired
    private FieldsProperties fieldsProperties;

    @Autowired
    private ConfigProperties configProperties;

    private List<FieldMarker> fieldMarkerList;

    protected abstract String getAuditEntityLine(AuditEntity audit, short auditTypeIdJournal);

    protected abstract String getCaptionsLine(short auditTypeIdJournal);

    protected abstract String getFileExtension();

    protected abstract String getContentType();

    private String getContentHeader() {
        return String.format("attachment; filename=\"%s.%s\"", configProperties.getFileName(), getFileExtension());
    }

    /*
    * Возвращает в потоке распарсенные строки записей аудита
    * Метод формирует файл на лету. При этом задаются различные параметры заголовков.
    * @param auditCriteria Класс с параметрами фильтрации
    * @param httpServletResponse Получает writer для записи строк в поток
    * */
    @Override
    @Transactional(readOnly = true)
    public void getAuditStream(AuditCriteria auditCriteria, HttpServletResponse httpServletResponse) throws IOException {
        setFieldMarkerList(auditCriteria.getAuditTypeId());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(getContentType());
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, getContentHeader());
        httpServletResponse.setCharacterEncoding("UTF-8");

        try (Stream<AuditEntity> auditStream = auditExportRepository.streamSearch(auditCriteria)) {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write(getCaptionsLine(auditCriteria.getAuditTypeId()));

            auditStream.forEach(
                    audit -> {
                        String line = getAuditEntityLine(audit, auditCriteria.getAuditTypeId());
                        printWriter.write(line);
                        printWriter.write("\n");
                    });

            printWriter.flush();
        } catch (IOException e) {
            throw new IOException("export.streamExportException", e);
        }
    }

    List<FieldMarker> getFieldMarkerList() {
        return fieldMarkerList;
    }

    private void setFieldMarkerList(short auditTypeIdJournal) {
        fieldMarkerList = fieldsProperties
                .getJournals()
                .get(StringUtils.lowerCase(AuditTypeCode.getCodeAuditType(auditTypeIdJournal)))
                .stream()
                .sorted(Comparator.comparingInt(FieldMarker::getOrder))
                .collect(Collectors.toList());

        if (fieldMarkerList.isEmpty()) {
            throw new UserException("export.notFoundFieldMarker");
        }
    }

    String getAuditFieldValue(AuditEntity audit, final String fieldName) {
        String value;

        switch (fieldName) {
            case "event_date":
                value = audit.getEventDate().format(DateTimeFormatter.ofPattern(configProperties.getDateFormat()));
                break;
            case "source_application":
                value = audit.getAuditSourceApplication();
                break;
            case "source_workstation":
                value = audit.getSourceWorkstation();
                break;
            case "hostname":
                value = audit.getHostname();
                break;
            case "object_id":
                value = audit.getObjectId();
                break;
            case "object_type":
                value = audit.getAuditObjectType();
                break;
            case "object_name":
                value = audit.getAuditObjectName();
                break;
            case "username":
                value = audit.getUsername();
                break;
            case "user_id":
                value = audit.getUserId();
                break;
            case "event_type":
                value = audit.getEventType();
                break;
            case "id":
                value = audit.getId().toString();
                break;
            case "sender_id":
                value = audit.getSenderId();
                break;
            case "receiver_id":
                value = audit.getReceiverId();
                break;
            case "context":
                value = audit.getContext();
                break;
            default:
                value = "";
                break;
        }

        return StringUtils.defaultString(value, configProperties.getIfNullValue());
    }
}
