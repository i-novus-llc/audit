package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectName;
import ru.i_novus.ms.audit.repository.AuditObjectNameRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectNameService {

    @Autowired
    private AuditObjectNameRepository auditObjectNameRepository;

    public Collection<AuditObjectName> getAll(){
        return auditObjectNameRepository.findAll();
    }

    public AuditObjectName getOrCreate(String name){
        Optional<AuditObjectName> optional = auditObjectNameRepository.findByName(name);

        return optional.orElseGet(() -> auditObjectNameRepository.save(AuditObjectName
                .builder()
                .name(name)
                .build()));
    }

    public AuditObjectName getById(UUID id){
        return auditObjectNameRepository.getOne(id);
    }
}
