package ru.i_novus.ms.audit.client.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.n2oapp.platform.jaxrs.MapperConfigurer;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.Audit;

@Component
public class AuditMapperConfigurer implements MapperConfigurer {
    @Override
    public void configure(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Audit.class, AuditMixIn.class);
    }
}
