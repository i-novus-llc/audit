package ru.i_novus.ms.audit.client.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.i_novus.ms.audit.client.security.context.ServerContext;
import ru.i_novus.ms.audit.client.security.context.UserContext;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Provider(Provider.Type.InInterceptor)
public class CustomInInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String UNKNOWN = "unknown";

    public CustomInInterceptor() {
        super(Phase.UNMARSHAL);
    }

    public void handleMessage(Message message) {

        ServerContext.setServerName(getServerName());

        Map<String, Object> protocolHeaders = (Map<String, Object>) message.get(Message.PROTOCOL_HEADERS);
        Object userHeader = protocolHeaders.get("userHeader");
        String userHeaderStr = null;
        if (userHeader == null) {
            userHeader = protocolHeaders.get("Authorization");
            if (userHeader != null) {
                String auth = ((List) userHeader).get(0).toString();
                auth = auth.replace("Bearer ", "");
                String[] jwtParts = auth.split("\\.");
                if (jwtParts.length > 1) {
                    userHeaderStr = jwtParts[1];
                } else {
                    userHeaderStr = jwtParts[0];
                }
            }
        } else {
            userHeaderStr = ((List) userHeader).get(0).toString();
        }

        CurrentAuthUser authUser = buildUser(userHeaderStr);
        UserContext.setAuthUser(authUser);

        ServerContext.setSourceWorkStation(getIpAddr());

    }

    private CurrentAuthUser buildUser(String userHeader) {
        if (userHeader == null) {
            return null;
        }

        String jwtBodyDecoded = new String(Base64.getDecoder().decode(userHeader));
        JsonNode jwtBodyNode;
        try {
            jwtBodyNode = MAPPER.readTree(jwtBodyDecoded);
        } catch (Exception e) {
            log.error("Error read value from string to json", e);
            return null;
        }

        JsonNode usernameNode = jwtBodyNode.get("username");
        JsonNode userIdNode = jwtBodyNode.has("email") ? jwtBodyNode.get("email") : jwtBodyNode.get("userId");

        return CurrentAuthUser.builder()
                .userId(userIdNode != null ? userIdNode.asText() : null)
                .username(usernameNode != null ? usernameNode.asText() : null)
                .build();
    }

    private String getIpAddr() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("sourceWorkStationHeader");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getServerName() {
        HttpServletRequest request = getRequest();

        return request == null ? null : request.getServerName();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.info("Request attributes is empty");
            return null;
        }

        return attributes.getRequest();
    }
}
