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

package ru.i_novus.ms.audit;

import net.n2oapp.platform.i18n.Messages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.i_novus.ms.audit.filter.AuditFilter;
import ru.i_novus.ms.audit.queue.Consumer;

@Configuration
public class AuditServiceConfig {

    @Bean
    public FilterRegistrationBean<AuditFilter> auditFilter(@Autowired Messages messages,
                                                           @Autowired Environment environment) {
        FilterRegistrationBean<AuditFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuditFilter(messages));

        //Получаем паттерн для регистрации фильтра из переменной "cxf.path", если она не указана - значит используется дефолтный "/services/"
        String baseUrlPattern = environment.getProperty("cxf.path");
        baseUrlPattern = StringUtils.isEmpty(baseUrlPattern) ? "/services/" : StringUtils.appendIfMissing(baseUrlPattern, "/");
        registrationBean.addUrlPatterns(baseUrlPattern + "audit");

        return registrationBean;
    }

    @Bean
    public Consumer auditConsumer() {
        return new Consumer();
    }
}
