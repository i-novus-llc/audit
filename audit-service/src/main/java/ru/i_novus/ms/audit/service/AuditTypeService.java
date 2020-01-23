package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditTypeEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditTypeBuilder;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;
import ru.i_novus.ms.audit.enums.AuditTypeEnum;
import ru.i_novus.ms.audit.model.AuditType;
import ru.i_novus.ms.audit.repository.AuditTypeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public Collection<AuditType> getDefaultAuditTypes() {
        List<AuditTypeEntity> auditTypeEntities = auditTypeRepository.findAllById(
                Stream.of(AuditTypeEnum.values())
                        .map(AuditTypeEnum::getId)
                        .collect(Collectors.toList()));

        return auditTypeEntities.stream()
                .map(AuditTypeBuilder::buildByEntity)
                .collect(Collectors.toList());
    }

    public void createIfNotPresent(Short id) {
        Optional<AuditTypeEntity> auditTypeEntity = auditTypeRepository.findById(id);
        if (auditTypeEntity.isEmpty()) {
            auditTypeRepository.save(AuditTypeEntityBuilder.buildDefaultAuditType(id));
        }
    }
}
