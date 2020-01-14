package ru.i_novus.ms.audit.client;

import org.junit.Before;
import org.junit.Test;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.filter.AuditClientFilter;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.util.AuditClientRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class AuditClientFilterTest {

    private static HttpServletRequest request = mock(HttpServletRequest.class);
    private static HttpServletResponse response = mock(HttpServletResponse.class);
    private static FilterChain chain = mock(FilterChain.class);
    private static AuditClient client = mock(AuditClient.class);
    private static AuditClientFilter filter = new AuditClientFilter(client);
    private static AuditClientRequest auditClientRequest = new AuditClientRequest();

    //Дефолтные значения используемые в фильтре
    private static final String OBJECT_TYPE = "REQUEST";
    private static final String OBJECT_NAME = "Запрос";
    private static final Short AUDIT_TYPE = 1;

    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    //Параметры которые берутся из реквеста
    private static final String REQUEST_METHOD = "GET";
    private static final String REQUEST_URL = "http://test.org/test";
    private static final String REQUEST_USER_AGENT = "test/5.0";
    private static final String REQUEST_CONTENT_TYPE = "application/json";
    private static final String REQUEST_SERVER_NAME = "http://test.org";
    private static final String IP_ADDRESS = "127.0.0.1";

    //Параметры которые берутся из респонса
    private static final int RESPONSE_STATUS_CODE = 200;
    private static final String RESPONSE_USER_AGENT = "test/5.1";
    private static final String RESPONSE_CONTENT_TYPE = "text/html";

    private static final String CONTEXT = "{\"General\":" +
            "{\"Request URL\":\"" + REQUEST_URL +
            "\",\"Request Method\":\"" + REQUEST_METHOD +
            "\",\"Status Code\":" + RESPONSE_STATUS_CODE + "}," +
            "\"RequestHeaders\":" +
            "{\"" + HEADER_USER_AGENT + "\":\"" + REQUEST_USER_AGENT +
            "\",\"" + HEADER_CONTENT_TYPE + "\":\"" + REQUEST_CONTENT_TYPE + "\"}," +
            "\"ResponseHeaders\":" +
            "{\"" + HEADER_USER_AGENT + "\":\"" + RESPONSE_USER_AGENT + "\"," +
            "\"" + HEADER_CONTENT_TYPE + "\":\"" + RESPONSE_CONTENT_TYPE + "\"}}";


    @Before
    public void beforeEach() {
        when(request.getMethod()).thenReturn(REQUEST_METHOD);
        when(request.getRequestURL()).thenReturn(new StringBuffer(REQUEST_URL));
        when(request.getServerName()).thenReturn(REQUEST_SERVER_NAME);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Arrays.asList(HEADER_USER_AGENT, HEADER_CONTENT_TYPE)));
        when(request.getHeader(HEADER_USER_AGENT)).thenReturn(REQUEST_USER_AGENT);
        when(request.getHeader(HEADER_CONTENT_TYPE)).thenReturn(REQUEST_CONTENT_TYPE);
        when(request.getRemoteAddr()).thenReturn(IP_ADDRESS);

        when(response.getStatus()).thenReturn(RESPONSE_STATUS_CODE);
        when(response.getHeaderNames()).thenReturn(Arrays.asList(HEADER_USER_AGENT, HEADER_CONTENT_TYPE));
        when(response.getHeader(HEADER_USER_AGENT)).thenReturn(RESPONSE_USER_AGENT);
        when(response.getHeader(HEADER_CONTENT_TYPE)).thenReturn(RESPONSE_CONTENT_TYPE);


        auditClientRequest.setEventType(REQUEST_METHOD);
        auditClientRequest.setObjectType(OBJECT_TYPE);
        auditClientRequest.setObjectId(REQUEST_URL);
        auditClientRequest.setObjectName(OBJECT_NAME);
        auditClientRequest.setSourceWorkstation(IP_ADDRESS);
        auditClientRequest.setAuditType(AUDIT_TYPE);
        auditClientRequest.setHostname(REQUEST_SERVER_NAME);
        auditClientRequest.setContext(CONTEXT);
    }

    @Test
    public void doFilterSuccess() throws IOException, ServletException {
        filter.doFilter(request, response, chain);
        verify(client).add(argThat(new AuditClientRequestMatcher(auditClientRequest)));
    }
}
