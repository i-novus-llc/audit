package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditObjectEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditObjectBuilder;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.repository.AuditObjectRepository;

import java.util.Optional;

@Service
public class AuditObjectService {

    @Autowired
    private AuditObjectRepository auditObjectRepository;

    public Page<AuditObject> search(AuditObjectCriteria criteria) {
        return auditObjectRepository.findAll(
                QueryService.toPredicate(criteria),
                PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getDefaultOrders()))
        ).map(AuditObjectBuilder::buildByEntity);
    }

    public void createIfNotPresent(String name, String type) {
        Optional<AuditObjectEntity> auditObjectEntity = auditObjectRepository.findByNameAndType(name, type);

        if (auditObjectEntity.isEmpty()) {
            auditObjectRepository.save(AuditObjectEntityBuilder.buildEntity(name, type));
        }
    }
}
