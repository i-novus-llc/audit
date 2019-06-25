package ru.i_novus.ms.audit.service;

import com.querydsl.core.types.Predicate;
import net.n2oapp.criteria.api.CollectionPage;
import net.n2oapp.criteria.api.CollectionPageService;
import net.n2oapp.criteria.api.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.entity.AuditEnt;
import ru.i_novus.ms.audit.entity.AuditObjectName;
import ru.i_novus.ms.audit.entity.AuditObjectType;
import ru.i_novus.ms.audit.entity.AuditSourceApplication;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.*;

@Service
public class AuditService implements CollectionPageService<Criteria, AuditEnt> {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ObjectNameService objectNameService;

    @Autowired
    private ObjectTypeService objectTypeService;

    @Autowired
    private SourceApplicationService sourceApplicationService;

    @Override
    public CollectionPage<AuditEnt> getCollectionPage(Criteria criteria) {

        List<AuditEnt> auditEntList = auditRepository.findAll();

        return null;
    }

    public Optional<AuditEnt> getById(UUID id){
        return this.auditRepository.findById(id);
    }


    public Page<Audit> search(AuditCriteria criteria){
        return (searchEntity(criteria)).map(AuditService::getAuditByEntity);
    }

    public Page<AuditEnt> searchEntity(AuditCriteria criteria){
        Pageable pageable = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(), QueryService.toSort(criteria));
        Page<AuditEnt> page = auditRepository.findAll(QueryService.toPredicate(criteria), pageable);
        return page;
    }


    public AuditEnt create(AuditForm request){

        AuditObjectName auditObjectName = objectNameService.getOrCreate(request.getObjectName());
        AuditObjectType auditObjectType = objectTypeService.getOrCreate(request.getObjectType());
        AuditSourceApplication auditSourceApplication = sourceApplicationService.getOrCreate(request.getSourceApplication());

        AuditEnt entity = AuditEnt.builder()
                .auditObjectName(auditObjectName)
                .auditObjectType(auditObjectType)
                .auditSourceApplication(auditSourceApplication)
                .context(request.getContext())
                .eventDate(request.getEventDate())
                .eventType(request.getEventType())
                .userId(request.getUserId())
                .username(request.getUsername())
                .objectId(request.getObjectId())
                .sourceWorkstation(request.getSourceWorkstation())
                .hostname(request.getHostname())
                .build();

        return auditRepository.save(entity);
    }


    public static Audit getAuditByEntity(AuditEnt entity){
        Audit audit = Audit.builder()
                    .creationDate(entity.getCreationDate())
                    .id(entity.getId())
                    .context(entity.getContext())
                    .eventDate(entity.getEventDate())
                    .eventType(entity.getEventType())
                    .objectId(entity.getObjectId())
                    .objectName(entity.getAuditObjectName().getName())
                    .objectType(entity.getAuditObjectType().getName())
                    .sourceApplication(entity.getAuditSourceApplication().getName())
                    .sourceWorkstation(entity.getSourceWorkstation())
                    .userId(entity.getUserId())
                    .hostname(entity.getHostname())
                    .username(entity.getUsername())
                .build();

        return audit;
    }
}
