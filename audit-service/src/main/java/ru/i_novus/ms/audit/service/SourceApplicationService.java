package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.model.AuditSourceApplicationBuilder;
import ru.i_novus.ms.audit.builder.entity.AuditSourceApplicationEntityBuilder;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.model.AuditSourceApplication;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SourceApplicationService {

    @Autowired
    private AuditSourceApplicationRepository auditSourceApplicationRepository;

    public Collection<AuditSourceApplication> getAll() {
        return auditSourceApplicationRepository.findAll()
                .stream().map(AuditSourceApplicationBuilder::buildByEntity)
                .collect(Collectors.toList());
    }

    public Page<AuditSourceApplication> search(AuditSourceApplicationCriteria criteria) {
        return auditSourceApplicationRepository.findAll(
                        QueryService.toPredicate(criteria),
                        PageRequest.of(0, criteria.getPageSize()))
                .map(AuditSourceApplicationBuilder::buildByEntity);
    }

    public void createIfNotPresent(String code) {
        Optional<AuditSourceApplicationEntity> optional = auditSourceApplicationRepository.findByCode(code);
        if (optional.isEmpty()) {
            auditSourceApplicationRepository.save(AuditSourceApplicationEntityBuilder.buildEntity(code));
        }
    }

    public AuditSourceApplication getById(UUID id) {
        return AuditSourceApplicationBuilder.buildByEntity(auditSourceApplicationRepository.getOne(id));
    }
}
