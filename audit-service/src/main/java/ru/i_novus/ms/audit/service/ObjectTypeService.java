package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.repository.AuditObjectTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectTypeService {

    @Autowired
    private AuditObjectTypeRepository auditObjectTypeRepository;

    public Collection<AuditObjectEntity> getAll(){
        return auditObjectTypeRepository.findAll();
    }

    public AuditObjectEntity getOrCreate(String name){
        Optional<AuditObjectEntity> optional = auditObjectTypeRepository.findByName(name);

        return optional.orElseGet(() -> auditObjectTypeRepository.save(AuditObjectEntity
                .builder()
                .name(name)
                .build()));
    }

    public AuditObjectEntity getById(UUID id){
        return auditObjectTypeRepository.getOne(id);
    }
}
