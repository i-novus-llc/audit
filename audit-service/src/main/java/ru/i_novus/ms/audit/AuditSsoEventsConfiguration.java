package ru.i_novus.ms.audit;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import ru.i_novus.ms.audit.service.AbstractSsoEventsService;
import ru.i_novus.ms.audit.service.BaseSsoEventsService;
import ru.i_novus.ms.audit.service.ExtendedSsoEventsService;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;


@Slf4j
@SpringBootConfiguration
@EnableConfigurationProperties(OpenIdProperties.class)
@EnableScheduling
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
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
        restTemplate.setRequestFactory(requestFactory());

        return restTemplate;
    }

    @Bean
    AbstractSsoEventsService ssoEventsService(OpenIdProperties openIdProperties) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Settings: \n" + mapper.writeValueAsString(openIdProperties));

        if (Boolean.TRUE.equals(openIdProperties.getUseEventsExtension())) {
            log.debug("Create ExtendedSsoEventsService.class");
            return new ExtendedSsoEventsService(openIdProperties);
        }

        log.debug("Create BaseSsoEventsService.class");
        return new BaseSsoEventsService(openIdProperties);
    }

    @Bean
    String getScheduleCronSyntax(OpenIdProperties openIdProperties) {
        return StringUtils.isEmpty(openIdProperties.getEventsSchedule()) ? "-" : openIdProperties.getEventsSchedule();
    }

    private ClientHttpRequestFactory requestFactory() {
        return new OkHttp3ClientHttpRequestFactory();
    }
}
