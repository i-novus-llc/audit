package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectTypeEntity;
import ru.i_novus.ms.audit.repository.AuditObjectTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectTypeService {

    @Autowired
    private AuditObjectTypeRepository auditObjectTypeRepository;

    public Collection<AuditObjectTypeEntity> getAll(){
        return auditObjectTypeRepository.findAll();
    }

    public AuditObjectTypeEntity getOrCreate(String name){
        Optional<AuditObjectTypeEntity> optional = auditObjectTypeRepository.findByName(name);

        return optional.orElseGet(() -> auditObjectTypeRepository.save(AuditObjectTypeEntity
                .builder()
                .name(name)
                .build()));
    }

    public AuditObjectTypeEntity getById(UUID id){
        return auditObjectTypeRepository.getOne(id);
    }
}
