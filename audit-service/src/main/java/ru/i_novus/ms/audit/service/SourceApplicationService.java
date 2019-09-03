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

    public Collection<AuditSourceApplicationEntity> getAll() {
        return auditSourceApplicationRepository.findAll();
    }

    public void createIfNotPresent(String code) {
        Optional<AuditSourceApplicationEntity> optional = auditSourceApplicationRepository.findByCode(code);
        if (optional.isEmpty()) {
            auditSourceApplicationRepository.save(AuditSourceApplicationEntity
                    .builder()
                    .code(code)
                    .build());
        }
    }

    public AuditSourceApplicationEntity getById(UUID id) {
        return auditSourceApplicationRepository.getOne(id);
    }
}
