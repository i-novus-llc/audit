/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.client.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.filter.AuditClientFilter;
import ru.i_novus.ms.audit.client.impl.StubAuditClientImpl;

import java.util.List;

@Configuration
public class AuditClientAutoConfiguration {

    @Value("${audit.client.sourceApplication:#{null}}")
    private String sourceApplication;

    @Value("${audit.client.filter-url-pattern:}")
    private List<String> filterUrlPattern;

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "sourceApplication")
    public SourceApplicationAccessor applicationAccessor() {
        return () -> sourceApplication;
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "enabled", havingValue = "false")
    public AuditClient stubAuditClient() {
        return new StubAuditClientImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.client", name = "filter-url-pattern")
    public FilterRegistrationBean<AuditClientFilter> auditClientFilter(@Autowired AuditClient auditClient) {
        FilterRegistrationBean<AuditClientFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuditClientFilter(auditClient));
        registrationBean.addUrlPatterns(filterUrlPattern.toArray(new String[0]));

        return registrationBean;
    }
}
