package ru.i_novus.ms.audit.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.properties.CSVProperties;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(CSVProperties.class)
class CSVAuditExportServiceImpl extends AuditExportServiceImpl {

    @Autowired
    private CSVProperties csvProperties;

    @Override
    protected String getFileExtension() {
        return "csv";
    }

    @Override
    protected String getContentType() {
        return "text/comma-separated-values";
    }

    @Override
    protected String getCaptionsLine(short auditTypeIdJournal) {
        if (!csvProperties.isPrintFieldName()) {
            return "";
        }

        List<String> fields = new ArrayList<>();
        getFieldMarkerList().forEach(fieldMarker -> fields.add(fieldMarker.getCaption()));
        return StringUtils.join(fields, csvProperties.getFieldDelimiter()) + "\n";
    }

    @Override
    protected String getAuditEntityLine(AuditEntity audit, short auditTypeIdJournal) {
        List<String> values = new ArrayList<>();

        getFieldMarkerList().forEach(
                fieldMarker -> values.add(
                        escapeSpecialCharacters(getAuditFieldValue(audit, fieldMarker.getName()))
                )
        );

        return StringUtils.join(values, csvProperties.getFieldDelimiter());
    }

    private String escapeSpecialCharacters(String value) {
        String escapedValue = value.replaceAll("\\R", " ");
        if (StringUtils.containsAny(value, csvProperties.getFieldDelimiter(), "\"", "'")) {
            value = value.replace("\"", "\"\"");
            escapedValue = String.format("\"%s\"", value);
        }
        return escapedValue;
    }
}
