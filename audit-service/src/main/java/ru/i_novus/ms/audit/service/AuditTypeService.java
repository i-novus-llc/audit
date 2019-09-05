package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.model.AuditTypeBuilder;
import ru.i_novus.ms.audit.model.AuditType;
import ru.i_novus.ms.audit.repository.AuditTypeRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public Collection<AuditType> getAll() {
        return auditTypeRepository.findAll()
                .stream()
                .map(AuditTypeBuilder::buildByEntity)
                .collect(Collectors.toList());
    }
}
