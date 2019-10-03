package ru.i_novus.ms.audit.client.app;

import net.n2oapp.platform.i18n.Messages;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.SourceWorkstationAccessor;
import ru.i_novus.ms.audit.client.UserAccessor;
import ru.i_novus.ms.audit.client.impl.converter.RequestConverter;

public class TestedRequestConverter extends RequestConverter {

    public TestedRequestConverter(UserAccessor userAccessor,
                                  SourceWorkstationAccessor sourceWorkstationAccessor,
                                  SourceApplicationAccessor sourceApplicationAccessor,
                                  Messages messages) {
        setUserAccessor(userAccessor);
        setWorkstationAccessor(sourceWorkstationAccessor);
        setApplicationAccessor(sourceApplicationAccessor);
        setMessages(messages);
    }
}
