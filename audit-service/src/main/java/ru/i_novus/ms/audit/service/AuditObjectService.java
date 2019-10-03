package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditObjectEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditObjectBuilder;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.repository.AuditObjectRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditObjectService {

    @Autowired
    private AuditObjectRepository auditObjectRepository;

    public Collection<AuditObject> getAll() {
        return auditObjectRepository.findAll().stream()
                .map(AuditObjectBuilder::buildByEntity)
                .collect(Collectors.toList());
    }

    public Page<AuditObject> search(AuditObjectCriteria criteria) {
        return auditObjectRepository.findAll(
                QueryService.toPredicate(criteria),
                PageRequest.of(0, criteria.getPageSize())
        ).map(AuditObjectBuilder::buildByEntity);
    }

    public void createIfNotPresent(String name, String type) {
        Optional<AuditObjectEntity> auditObjectEntity = auditObjectRepository.findByNameAndType(name, type);

        if (auditObjectEntity.isEmpty()) {
            auditObjectRepository.save(AuditObjectEntityBuilder.buildEntity(name, type));
        }
    }

    public AuditObject getById(Integer id) {
        return AuditObjectBuilder.buildByEntity(auditObjectRepository.getOne(id));
    }
}
