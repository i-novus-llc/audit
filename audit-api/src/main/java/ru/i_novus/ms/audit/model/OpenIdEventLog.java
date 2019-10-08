package ru.i_novus.ms.audit.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class OpenIdEventLog {
    private Date time;
    private String type;
    private String realmId;
    private String clientId;
    private String userId;
    private String sessionId;
    private String ipAddress;
    private String error;
    private Map<String, String> details;

}
