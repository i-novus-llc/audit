package ru.i_novus.ms.audit;

import net.n2oapp.framework.security.auth.oauth2.gateway.GatewayPrincipalExtractor;
import net.n2oapp.security.auth.oauth2.OpenIdSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
public class SecurityConfig extends OpenIdSecurityConfigurerAdapter {

    @Override
    protected void authorize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                                     .ExpressionInterceptUrlRegistry url) throws Exception {
        url.anyRequest().authenticated();
    }

    @Bean
    public GatewayPrincipalExtractor gatewayPrincipalExtractor() {
        return new GatewayPrincipalExtractor();
    }
}
