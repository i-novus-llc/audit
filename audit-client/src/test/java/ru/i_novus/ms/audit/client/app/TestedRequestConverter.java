package ru.i_novus.ms.audit.client.app;

import net.n2oapp.platform.i18n.Messages;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;

public class TestedRequestConverter extends RequestConverter {

    public TestedRequestConverter(SourceApplicationAccessor sourceApplicationAccessor,
                                  Messages messages) {
        setApplicationAccessor(sourceApplicationAccessor);
        setMessages(messages);
    }
}
