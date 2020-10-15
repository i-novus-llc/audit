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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {
    private static final ThreadLocal<CurrentAuthUser> AUTH_USER = new ThreadLocal<>();

    public static CurrentAuthUser getAuthUser() {
        return AUTH_USER.get();
    }

    public static void setAuthUser(CurrentAuthUser user) {
        AUTH_USER.set(user);
    }

    public static void removeAuthUser() {
        AUTH_USER.remove();
    }
}
