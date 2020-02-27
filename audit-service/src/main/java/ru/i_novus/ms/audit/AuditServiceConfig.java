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
