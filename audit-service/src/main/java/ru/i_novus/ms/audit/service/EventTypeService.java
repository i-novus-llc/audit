package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.model.AuditEventTypeBuilder;
import ru.i_novus.ms.audit.builder.entity.AuditEventTypeEntityBuilder;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.repository.EventTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public Collection<AuditEventType> getAll() {
        return eventTypeRepository.findAll().stream()
                .map(AuditEventTypeBuilder::buildByEntity)
                .collect(Collectors.toList());
    }

    public Page<AuditEventType> search(AuditEventTypeCriteria criteria) {

        return eventTypeRepository.findAll(
                        QueryService.toPredicate(criteria),
                        PageRequest.of(0, criteria.getPageSize())
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

    public AuditEventType getById(UUID id) {
        return AuditEventTypeBuilder.buildByEntity(eventTypeRepository.getOne(id));
    }
}
