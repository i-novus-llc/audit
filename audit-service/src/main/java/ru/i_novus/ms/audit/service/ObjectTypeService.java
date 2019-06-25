package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectType;
import ru.i_novus.ms.audit.repository.AuditObjectTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectTypeService {

    @Autowired
    private AuditObjectTypeRepository auditObjectTypeRepository;

    public Collection<AuditObjectType> getAll(){
        return auditObjectTypeRepository.findAll();
    }

    public AuditObjectType getOrCreate(String name){
        Optional<AuditObjectType> optional = auditObjectTypeRepository.findByName(name);

        return optional.orElseGet(() -> auditObjectTypeRepository.save(AuditObjectType
                .builder()
                .name(name)
                .build()));
    }

    public AuditObjectType getById(UUID id){
        return auditObjectTypeRepository.getOne(id);
    }
}
