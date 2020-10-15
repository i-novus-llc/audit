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

package ru.i_novus.ms.audit.client.security.context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Тестирование класса ServerContext
 *
 * @author akuznetcov
 **/
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
