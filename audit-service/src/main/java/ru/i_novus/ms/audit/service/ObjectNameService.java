package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectNameEntity;
import ru.i_novus.ms.audit.repository.AuditObjectNameRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectNameService {

    @Autowired
    private AuditObjectNameRepository auditObjectNameRepository;

    public Collection<AuditObjectNameEntity> getAll(){
        return auditObjectNameRepository.findAll();
    }

    public AuditObjectNameEntity getOrCreate(String name){
        Optional<AuditObjectNameEntity> optional = auditObjectNameRepository.findByName(name);

        return optional.orElseGet(() -> auditObjectNameRepository.save(AuditObjectNameEntity
                .builder()
                .name(name)
                .build()));
    }

    public AuditObjectNameEntity getById(UUID id){
        return auditObjectNameRepository.getOne(id);
    }
}
