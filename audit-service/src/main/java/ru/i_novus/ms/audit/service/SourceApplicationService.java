package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class SourceApplicationService {

    @Autowired
    private AuditSourceApplicationRepository auditSourceApplicationRepository;

    public Collection<AuditSourceApplicationEntity> getAll(){
        return auditSourceApplicationRepository.findAll();
    }

    public AuditSourceApplicationEntity getOrCreate(String name){
        Optional<AuditSourceApplicationEntity> optional = auditSourceApplicationRepository.findByName(name);

        return optional.orElseGet(() -> auditSourceApplicationRepository.save(AuditSourceApplicationEntity
                .builder()
                .name(name)
                .build()));
    }

    public AuditSourceApplicationEntity getById(UUID id){
        return auditSourceApplicationRepository.getOne(id);
    }
}
