package ru.i_novus.ms.audit.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.n2oapp.platform.jaxrs.MapperConfigurer;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.util.json.LocalDateTimeISOSerializer;

@Component
public class LocalDateTimeMapperPreparer implements MapperConfigurer {
    @Override
    public void configure(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new LocalDateTimeISOSerializer());
        objectMapper.registerModule(module);
    }
}