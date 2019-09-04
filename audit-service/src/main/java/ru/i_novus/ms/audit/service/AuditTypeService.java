package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;
import ru.i_novus.ms.audit.repository.AuditTypeRepository;

import java.util.Collection;

@Service
public class AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public Collection<AuditTypeEntity> getAll() {
        return auditTypeRepository.findAll();
    }
}
