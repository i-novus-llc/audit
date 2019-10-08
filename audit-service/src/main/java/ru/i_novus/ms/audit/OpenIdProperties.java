package ru.i_novus.ms.audit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Настройки аутентификации OAuth2 OpenId Connect
 */

@ConfigurationProperties("audit.security.oauth2")
@Data
public class OpenIdProperties {
    private String authServerUri;
    private String accessTokenUri;
    private String clientId;
    private String clientSecret;
    private String[] scopes;
    private String code;
    private String eventsUrl;
    private String eventsSchedule;

    public String getAccessTokenUri() {
        return concatServer(accessTokenUri);
    }

    private String concatServer(String url) {
        if (StringUtils.startsWithIgnoreCase(url, "http")) {
            return url;
        } else {
            if (!StringUtils.startsWithIgnoreCase(url, "/"))
                throw new IllegalArgumentException("Part of url " + url + " must be started with /");
            return getAuthServerUri() + url;
        }
    }
}
