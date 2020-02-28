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
}
