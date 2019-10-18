package ru.i_novus.ms.audit.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.n2oapp.platform.i18n.Messages;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Фильтр для валидации запросов приходящих на /audit
 */
public class AuditFilter implements Filter {

    private static final String FIELD_AUDIT_TYPE = "auditType";
    private static final String FIELD_EVENT_DATE = "eventDate";
    private static final String FIELD_EVENT_DATE_FROM = "eventDateFrom";
    private static final String FIELD_EVENT_DATE_TO = "eventDateTo";

    private static final String CODE_IS_NUMERIC_NON_ZERO = "audit.filterError.isNumericNotZero";
    private static final String CODE_IS_DATE = "audit.filterError.isDate";
    private static final String CODE_TOO_BIG_PERIOD = "audit.filterError.tooBigPeriod";
    private static final String CODE_NEGATIVE_PERIOD = "audit.filterError.negativePeriod";

    private static final Logger logger = LoggerFactory.getLogger(AuditFilter.class);
    private Messages messages;

    AuditFilter(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            postFilter(httpRequest, httpResponse, chain);
        } else if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
            getFilter(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void postFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {
        RequestWrapper wrappedRequest = new RequestWrapper(request);
        JsonNode requestJson = new ObjectMapper().readTree(wrappedRequest.getReader());

        JsonNode eventDateJson = requestJson.get(FIELD_EVENT_DATE);
        JsonNode auditTypeJson = requestJson.get(FIELD_AUDIT_TYPE);

        List<String> errors = new ArrayList<>();
        if (eventDateJson != null && !isDate(eventDateJson.asText())) {
            errors.add(
                    messages.getMessage(
                            CODE_IS_DATE, FIELD_EVENT_DATE, eventDateJson.asText()));
        }

        if (auditTypeJson != null && NumberUtils.toShort(auditTypeJson.asText()) == 0) {
            errors.add(
                    messages.getMessage(
                            CODE_IS_NUMERIC_NON_ZERO, FIELD_AUDIT_TYPE, auditTypeJson.asText()));
        }

        sendResponse(wrappedRequest, response, chain, joinErrorMessages(errors));
    }

    private void getFilter(HttpServletRequest request,
                           HttpServletResponse response,
                           FilterChain chain)
            throws IOException, ServletException {
        String auditType = request.getParameter(FIELD_AUDIT_TYPE);
        String eventDateFrom = request.getParameter(FIELD_EVENT_DATE_FROM);
        String eventDateTo = request.getParameter(FIELD_EVENT_DATE_TO);

        List<String> errors = new ArrayList<>();
        if (!isDate(eventDateFrom)) {
            errors.add(
                    messages.getMessage(
                            CODE_IS_DATE, FIELD_EVENT_DATE_FROM, eventDateFrom));
        }

        if (!isDate(eventDateTo)) {
            errors.add(
                    messages.getMessage(
                            CODE_IS_DATE, FIELD_EVENT_DATE_TO, eventDateTo));
        }

        if (isDate(eventDateFrom) && isDate(eventDateTo)) {
            long daysBetweenDates = Duration.between(LocalDateTime.parse(eventDateFrom), LocalDateTime.parse(eventDateTo)).toDays();
            if (daysBetweenDates > 31) {
                errors.add(
                        messages.getMessage(
                                CODE_TOO_BIG_PERIOD, FIELD_EVENT_DATE_FROM, eventDateFrom, FIELD_EVENT_DATE_TO, eventDateTo));
            } else if (daysBetweenDates < 0) {
                errors.add(
                        messages.getMessage(
                                CODE_NEGATIVE_PERIOD, FIELD_EVENT_DATE_FROM, eventDateFrom, FIELD_EVENT_DATE_TO, eventDateTo));
            }
        }

        if (NumberUtils.toShort(auditType) == 0) {
            errors.add(
                    messages.getMessage(
                            CODE_IS_NUMERIC_NON_ZERO, FIELD_AUDIT_TYPE, auditType));
        }

        sendResponse(request, response, chain, joinErrorMessages(errors));
    }

    /**
     * Метод для проверки парсится ли строка в LocalDateTime в формате yyyy-MM-ddTHH:mm:ss
     *
     * @param date Проверяемая строка
     * @return true - парсится, false - не парсится
     */
    private boolean isDate(String date) {
        if (date == null) {
            return false;
        }

        try {
            LocalDateTime.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Метод для формирования сообщения об ошибке в json формате. Возращает null в случае если список ошибок был пуст
     *
     * @param errors Список ошибок
     * @return Строка с ошибками в json формате, null если список ошибок пуст
     */
    private String joinErrorMessages(List<String> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            return null;
        }

        StringBuilder errorMessage = new StringBuilder("{\"errors\":[");
        for (String value : errors) {
            errorMessage.append(value);
            errorMessage.append(",");
        }
        //Удаление лишней запятой
        errorMessage.setLength(errorMessage.length() - 1);
        errorMessage.append("]}");

        return errorMessage.toString();
    }

    /**
     * Метод отправляющий запрос дальше в FilterChain или формирующий response с ошибкой в том случае если запрос не прошел валидаицю
     *
     * @param request      обрабатываемый запрос
     * @param response     ответ связанный с обрабатываемым запросом
     * @param chain        FilterChain для передачи запроса и ответа следующим фильтрам
     * @param errorMessage сообщение об ошибке
     */
    private void sendResponse(HttpServletRequest request,
                              HttpServletResponse response,
                              FilterChain chain,
                              String errorMessage)
            throws IOException, ServletException {
        if (errorMessage == null) {
            chain.doFilter(request, response);
        } else {
            logRequest(request);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(errorMessage);
            response.getWriter().flush();
        }
    }

    /**
     * Метод для логирования пришедшего запроса
     *
     * @param request Входящий POST/GET запрос
     */
    private void logRequest(HttpServletRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String key = e.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }

        String logMessage = String.format("REQ_IN %nAddress: %s %nHttpMethod: %s %nHeaders: %s",
                request.getRequestURL(), request.getMethod(), headers.toString());
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            logMessage = String.format("%s%nPayload: %s", logMessage, request.getReader().readLine());
        }
        logMessage = String.format("%s%n", logMessage);
        logger.info(logMessage);
    }
}
