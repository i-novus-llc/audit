package ru.i_novus.ms.audit.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.EventTypeEntity;
import ru.i_novus.ms.audit.model.EventTypeCriteria;
import ru.i_novus.ms.audit.repository.EventTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public Collection<EventTypeEntity> getAll() {
        return eventTypeRepository.findAll();
    }

    public Collection<EventTypeEntity> search(EventTypeCriteria criteria) {
        return Lists.newArrayList(eventTypeRepository.findAll(QueryService.toPredicate(criteria)));
    }

    public void createIfNotPresent(String name, String auditType) {
        Optional<EventTypeEntity> eventTypeEntity = eventTypeRepository.findByNameAndAuditTypeId(name, auditType);

        if (eventTypeEntity.isEmpty()) {
            eventTypeRepository.save(EventTypeEntity
                    .builder()
                    .name(name)
                    .auditTypeId(Integer.valueOf(auditType))
                    .build());
        }
    }

    public EventTypeEntity getById(UUID id) {
        return eventTypeRepository.getOne(id);
    }
}
