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

import org.junit.Before;
import org.junit.Test;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;

import static org.junit.Assert.*;

/**
 * Тестирование класса UserContext
 *
 * @author akuznetcov
 **/
public class UserContextTest {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";

    private CurrentAuthUser currentAuthUser;

    @Before
    public void init() {
        currentAuthUser = CurrentAuthUser.builder()
                                        .userId(USER_ID)
                                        .username(USER_NAME)
                                        .build();
    }

    @Test
    public void userContextCurrentAuthUserTest() {
        UserContext.setAuthUser(currentAuthUser);
        assertNotNull(UserContext.getAuthUser());
        assertEquals(currentAuthUser, UserContext.getAuthUser());
        assertEquals(USER_NAME, UserContext.getAuthUser().getUsername());
        assertEquals(USER_ID, UserContext.getAuthUser().getUserId());

        UserContext.removeAuthUser();
        assertNull(UserContext.getAuthUser());
    }
}
