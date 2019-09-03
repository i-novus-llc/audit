package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.repository.AuditObjectRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuditObjectService {

    @Autowired
    private AuditObjectRepository auditObjectRepository;

    public Collection<AuditObjectEntity> getAll() {
        return auditObjectRepository.findAll();
    }

    public void createIfNotPresent(String name, String type) {
        Optional<AuditObjectEntity> auditObjectEntity = auditObjectRepository.findByNameAndType(name, type);

        if (auditObjectEntity.isEmpty()) {
            auditObjectRepository.save(AuditObjectEntity
                    .builder()
                    .name(name)
                    .type(type)
                    .build());
        }
    }

    public AuditObjectEntity getById(Integer id) {
        return auditObjectRepository.getOne(id);
    }
}
