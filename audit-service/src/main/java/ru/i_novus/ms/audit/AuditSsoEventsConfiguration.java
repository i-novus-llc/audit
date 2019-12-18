package ru.i_novus.ms.audit;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import ru.i_novus.ms.audit.service.SsoEventsService;

@SpringBootConfiguration
@EnableConfigurationProperties(OpenIdProperties.class)
public class AuditSsoEventsConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable();
    }

    @Bean
    OAuth2RestTemplate restTemplate(OpenIdProperties properties) {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(properties.getAccessTokenUri());
        resource.setClientId(properties.getClientId());
        resource.setClientSecret(properties.getClientSecret());
        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
    }

    @Bean
    SsoEventsService ssoEventsService(OpenIdProperties openIdProperties) {
        return new SsoEventsService(openIdProperties);
    }

    @Bean
    String getScheduleCronSyntax(OpenIdProperties openIdProperties) {
        return StringUtils.isEmpty(openIdProperties.getEventsSchedule()) ? "-" : openIdProperties.getEventsSchedule();
    }
}
