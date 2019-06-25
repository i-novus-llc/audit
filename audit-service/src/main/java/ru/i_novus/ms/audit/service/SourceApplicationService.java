package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditSourceApplication;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class SourceApplicationService {

    @Autowired
    private AuditSourceApplicationRepository auditSourceApplicationRepository;

    public Collection<AuditSourceApplication> getAll(){
        return auditSourceApplicationRepository.findAll();
    }

    public AuditSourceApplication getOrCreate(String name){
        Optional<AuditSourceApplication> optional = auditSourceApplicationRepository.findByName(name);

        return optional.orElseGet(() -> auditSourceApplicationRepository.save(AuditSourceApplication
                .builder()
                .name(name)
                .build()));
    }

    public AuditSourceApplication getById(UUID id){
        return auditSourceApplicationRepository.getOne(id);
    }
}
