package ru.i_novus.ms.audit.mapper;

import net.n2oapp.platform.i18n.Message;
import net.n2oapp.platform.i18n.Messages;
import net.n2oapp.platform.jaxrs.RestExceptionMapper;
import net.n2oapp.platform.jaxrs.RestMessage;
import ru.i_novus.ms.audit.exception.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

/**
 * Конвертация исключения {@link NotFoundException}, содержащего локализованное сообщение, в ответ REST сервиса.
 */
@Provider
public class AuditNotFoundExceptionMapper implements RestExceptionMapper<NotFoundException> {
    private Messages messages;

    AuditNotFoundExceptionMapper(Messages messages) {
        this.messages = messages;
    }

    public RestMessage toMessage(NotFoundException exception) {
        if (exception.getMessages() != null) {
            return new RestMessage(exception.getMessages().stream().map(this::toError).collect(Collectors.toList()));
        }
        return new RestMessage(messages.getMessage(exception.getMessage(), exception.getArgs()));
    }

    private RestMessage.Error toError(Message message) {
        return new RestMessage.Error(messages.getMessage(message.getCode(), message.getArgs()));
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }
}