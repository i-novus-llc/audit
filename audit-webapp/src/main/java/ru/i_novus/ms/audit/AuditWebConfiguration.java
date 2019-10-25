package ru.i_novus.ms.audit;

import net.n2oapp.framework.api.context.ContextProcessor;
import net.n2oapp.framework.api.data.DomainProcessor;
import net.n2oapp.framework.api.data.QueryExceptionHandler;
import net.n2oapp.framework.api.data.QueryProcessor;
import net.n2oapp.framework.engine.data.N2oInvocationFactory;
import net.n2oapp.framework.engine.data.N2oQueryProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditWebConfiguration {
    @Bean
    public QueryProcessor saQueryProcessor(ContextProcessor contextProcessor,
                                           DomainProcessor domainProcessor,
                                           N2oInvocationFactory invocationFactory,
                                           QueryExceptionHandler exceptionHandler) {
        N2oQueryProcessor queryProcessor = new N2oQueryProcessor(invocationFactory, contextProcessor, domainProcessor,
                exceptionHandler);
        queryProcessor.setCriteriaResolver(new RestCriteriaConstructor());
        queryProcessor.setPageStartsWith0(true);
        return queryProcessor;
    }
}
