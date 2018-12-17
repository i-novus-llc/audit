package ru.i_novus.ms.audit.provider;

import ru.i_novus.ms.audit.util.TimeUtils;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.UUID;

@Provider
public class AuditParamConverterProvider implements javax.ws.rs.ext.ParamConverterProvider {

    private LocalDateTimeParamConverter localDateParamConverter = new LocalDateTimeParamConverter();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

        if (LocalDateTime.class.equals(rawType))
            //noinspection unchecked
            return (ParamConverter<T>) localDateParamConverter;
        return null;
    }

    private static class LocalDateTimeParamConverter implements ParamConverter<LocalDateTime> {

        @Override
        public LocalDateTime fromString(String str) {
            return TimeUtils.parseLocalDateTime(str);
        }

        @Override
        public String toString(LocalDateTime value) {
            return TimeUtils.format(value);
        }
    }

    private static class UUIDParamConverter implements ParamConverter<UUID> {

        @Override
        public UUID fromString(String str) {
            return UUID.fromString(str);
        }

        @Override
        public String toString(UUID value) {
            return value.toString();
        }
    }
}