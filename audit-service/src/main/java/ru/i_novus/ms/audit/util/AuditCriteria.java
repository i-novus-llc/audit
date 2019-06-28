package ru.i_novus.ms.audit.util;

import lombok.Getter;
import lombok.Setter;
import net.n2oapp.criteria.api.Criteria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AuditCriteria extends Criteria {

    private UUID id;
    private LocalDateTime eventDateFrom;
    private LocalDateTime eventDateTo;
    private String eventType;

    private LocalDateTime creationDate;
    private List<UUID> auditObjectTypeIds;
    private List<UUID> auditObjectNameIds;
    private String objectId;
    private String userId;
    private String username;
    private List<UUID> auditSourceApplicationIds;
    private String sourceWorkstation;
    private String context;

}
