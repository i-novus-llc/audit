package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditEventTypeEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditEventTypeBuilder;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.repository.EventTypeRepository;

import java.util.Optional;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public Page<AuditEventType> search(AuditEventTypeCriteria criteria) {

        return eventTypeRepository.findAll(
                        QueryService.toPredicate(criteria),
                        PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getDefaultOrders()))
        ).map(AuditEventTypeBuilder::buildByEntity);
    }

    public void createIfNotPresent(String name, Short auditType) {
        Optional<AuditEventTypeEntity> eventTypeEntity = eventTypeRepository.findByNameAndAuditTypeId(name, auditType);

        if (eventTypeEntity.isEmpty()) {
            eventTypeRepository.save(
                    AuditEventTypeEntityBuilder.buildEntity(name, auditType)
            );
        }
    }
}
