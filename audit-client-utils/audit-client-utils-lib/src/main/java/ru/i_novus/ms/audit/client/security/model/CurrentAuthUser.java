package ru.i_novus.ms.audit.client.security.model;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CurrentAuthUser {
    private String username;
    private String userId;
    private String orgCode;
}
