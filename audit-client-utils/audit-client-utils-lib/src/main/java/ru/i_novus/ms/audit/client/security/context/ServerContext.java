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
