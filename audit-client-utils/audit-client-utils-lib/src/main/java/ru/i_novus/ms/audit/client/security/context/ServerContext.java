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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerContext {
    private static final ThreadLocal<String> SERVER_NAME = new ThreadLocal<>();
    private static final ThreadLocal<String> SOURCE_WORKSTATION = new ThreadLocal<>();

    public static void setServerName(String serverName) {
        SERVER_NAME.set(serverName);
    }

    public static String getServerName() {
        return SERVER_NAME.get();
    }

    public static void removeServerName() {
        SERVER_NAME.remove();
    }

    public static void setSourceWorkStation(String serverName) {
        SOURCE_WORKSTATION.set(serverName);
    }

    public static String getSourceWorkStation() {
        return SOURCE_WORKSTATION.get();
    }

    public static void removeSourceWorkstation() {
        SOURCE_WORKSTATION.remove();
    }
}
