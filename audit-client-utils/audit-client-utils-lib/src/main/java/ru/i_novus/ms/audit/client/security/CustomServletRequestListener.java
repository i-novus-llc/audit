package ru.i_novus.ms.audit.client.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.client.security.context.ServerContext;
import ru.i_novus.ms.audit.client.security.context.UserContext;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
@Component
public class CustomServletRequestListener implements ServletRequestListener {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String UNKNOWN = "unknown";
    private static final String CUSTOM_USER_HEADER = "userHeader";
    private static final String CUSTOM_SOURCE_WORKSTATION_HEADER = "sourceWorkStationHeader";

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

        if (request == null) {
            return;
        }
        String userHeader = getUserHeader(request);
        CurrentAuthUser authUser = buildUser(userHeader);

        UserContext.setAuthUser(authUser);
        ServerContext.setServerName(request.getServerName());
        ServerContext.setSourceWorkStation(getIpAddr(request));
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        UserContext.removeAuthUser();
        ServerContext.removeServerName();
        ServerContext.removeSourceWorkstation();
    }

    private String getUserHeader(HttpServletRequest request) {
        String userHeader = request.getHeader(CUSTOM_USER_HEADER);
        if (userHeader == null) {
            userHeader = request.getHeader("Authorization");
            if (userHeader != null) {
                userHeader = userHeader.replace("Bearer ", "");
                String[] jwtParts = userHeader.split("\\.");
                userHeader = jwtParts.length > 1 ? jwtParts[1] : jwtParts[0];
            }
        }

        return userHeader;
    }

    private CurrentAuthUser buildUser(String userHeader) {
        if (userHeader == null) {
            return null;
        }

        String jwtBodyDecoded = new String(Base64.getDecoder().decode(userHeader));
        JsonNode jwtBodyNode;
        try {
            jwtBodyNode = mapper.readTree(jwtBodyDecoded);
        } catch (Exception e) {
            log.error("Error read value from string to json", e);
            return null;
        }

        JsonNode usernameNode = jwtBodyNode.get("username");
        JsonNode userIdNode = jwtBodyNode.has("email") ? jwtBodyNode.get("email") : jwtBodyNode.get("userId");

        if (userIdNode.isNull() && usernameNode.isNull()) {
            return null;
        }

        return CurrentAuthUser.builder()
                .userId(userIdNode.isNull() ? null : userIdNode.asText())
                .username(usernameNode.isNull() ? null : usernameNode.asText())
                .build();
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(CUSTOM_SOURCE_WORKSTATION_HEADER);
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
