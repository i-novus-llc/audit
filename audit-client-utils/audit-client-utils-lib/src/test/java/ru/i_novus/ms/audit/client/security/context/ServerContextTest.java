package ru.i_novus.ms.audit.client.security.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Тестирование класса ServerContext
 *
 * @author akuznetcov
 **/
@RunWith(MockitoJUnitRunner.class)
public class ServerContextTest {

    private static final String SERVER_NAME_VALUE = "SERVER_NAME";
    private static final String SOURCE_WORKSTATION_VALUE = "SOURCE_WORKSTATION";

    @Test
    public void serverContextFieldsTest() {
        ServerContext.setServerName(SERVER_NAME_VALUE);
        assertEquals(SERVER_NAME_VALUE, ServerContext.getServerName());

        ServerContext.setSourceWorkStation(SOURCE_WORKSTATION_VALUE);
        assertEquals(SOURCE_WORKSTATION_VALUE, ServerContext.getSourceWorkStation());

        ServerContext.removeServerName();
        assertNull(ServerContext.getServerName());

        ServerContext.removeSourceWorkstation();
        assertNull(ServerContext.getSourceWorkStation());
    }
}
