package ru.i_novus.ms.audit.client.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class AuditClientFilter implements Filter {

    private static final String OBJECT_TYPE = "REQUEST";
    private static final String OBJECT_NAME = "Запрос";
    private static final Short AUDIT_TYPE = 1;

    private AuditClient auditClient;

    public AuditClientFilter(AuditClient auditClient) {
        this.auditClient = auditClient;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(cachedRequest, cachedResponse);

        AuditClientRequest auditClientRequest = new AuditClientRequest();
        auditClientRequest.setEventType(cachedRequest.getMethod());
        auditClientRequest.setObjectType(OBJECT_TYPE);
        auditClientRequest.setObjectId(cachedRequest.getRequestURL() == null ? null : cachedRequest.getRequestURL().toString());
        auditClientRequest.setObjectName(OBJECT_NAME);
        String ipAddressXFF = cachedRequest.getHeader("X-Forwarded-For");
        auditClientRequest.setSourceWorkstation((ipAddressXFF == null) ? cachedRequest.getRemoteAddr() : ipAddressXFF);
        auditClientRequest.setAuditType(AUDIT_TYPE);
        auditClientRequest.setHostname(cachedRequest.getServerName());

        try {
            auditClientRequest.setContext(getContextJson(cachedRequest, cachedResponse));
            auditClient.add(auditClientRequest);
        } catch (Exception e) {
            log.error("Error occured while sending audit data", e);
        } finally {
            cachedResponse.copyBodyToResponse();
        }
    }

    private JsonNode getContextJson(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ObjectNode generalNode = mapper.createObjectNode();
        generalNode.put("Request URL", String.valueOf(request.getRequestURL()));
        generalNode.put("Request Method", request.getMethod());
        generalNode.put("Status Code", response.getStatusCode());
        rootNode.set("General", generalNode);

        List<String> requestHeaders = Collections.list(request.getHeaderNames());
        if (!CollectionUtils.isEmpty(requestHeaders)) {
            ObjectNode requestHeadersNode = mapper.createObjectNode();
            requestHeaders.forEach(header ->
                    requestHeadersNode.put(header, request.getHeader(header)));
            rootNode.set("RequestHeaders", requestHeadersNode);
        }

        byte[] requestPayload = request.getContentAsByteArray();
        if (requestPayload.length != 0) {
            JsonNode requestPayloadNode = mapper.readTree(requestPayload);
            rootNode.set("RequestPayload", requestPayloadNode);
        }

        Collection<String> responseHeaders = response.getHeaderNames();
        if (!CollectionUtils.isEmpty(responseHeaders)) {
            ObjectNode responseHeadersNode = mapper.createObjectNode();
            responseHeaders.forEach(header ->
                    responseHeadersNode.put(header, response.getHeader(header)));
            rootNode.set("ResponseHeaders", responseHeadersNode);
        }

        byte[] responsePayload = response.getContentAsByteArray();
        if (responsePayload.length != 0) {
            JsonNode responsePayloadNode = mapper.readTree(responsePayload);
            rootNode.set("ResponsePayload", responsePayloadNode);
        }

        return rootNode;
    }
}
