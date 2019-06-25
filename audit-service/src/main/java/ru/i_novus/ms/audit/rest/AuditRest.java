package ru.i_novus.ms.audit.rest;

import com.google.common.collect.Lists;
import net.n2oapp.criteria.api.CollectionPage;
import net.n2oapp.criteria.api.Criteria;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.i_novus.ms.audit.entity.AuditEnt;
import ru.i_novus.ms.audit.entity.AuditObjectName;
import ru.i_novus.ms.audit.exception.NotFoundException;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteriaDTO;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.AuditService;
import ru.i_novus.ms.audit.service.ObjectNameService;
import ru.i_novus.ms.audit.service.api.AuditControllerApi;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;


@RestController
public class AuditRest implements AuditControllerApi {

    @Autowired
    private AuditService auditService;

    @Override
    @GetMapping("/audit/{id}")
    public Audit getById(@PathVariable UUID id) {
        System.out.println("Asas");
        Optional<AuditEnt> auditEntity = auditService.getById(id);

        if (auditEntity.isEmpty())
            throw new NotFoundException();

        return AuditService.getAuditByEntity(auditEntity.get());
    }

    @Override
    @GetMapping("/audits")
    public Page<Audit> search(AuditCriteriaDTO criteria) {
       return auditService.search(new AuditCriteria(criteria));
    }

    @Override
    @PostMapping("/audit")
    public Audit add(@RequestBody AuditForm auditForm) {
        if (isNull(auditForm))
            throw new IllegalArgumentException();

        return AuditService.getAuditByEntity(auditService.create(auditForm));
    }



    @RequestMapping(method = RequestMethod.GET, value = "/auditN20")
    public CollectionPage<AuditEnt> getAudits(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @RequestParam(value = "sortingColumn", required = false, defaultValue = "eventDate") String sortingColumn,
                                              @RequestParam(value = "sortingOrder", required = false, defaultValue = "desc") String sortingOrder,
                                              AuditCriteria auditCriteria){
        auditCriteria.setPageNumber(page);
        auditCriteria.setPageSize(size);
        if (sortingColumn!=null && sortingOrder!=null) {
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortingOrder), sortingColumn);
            auditCriteria.setOrders(Lists.newArrayList(order));
        }
        Page<AuditEnt> auditPage = auditService.searchEntity(auditCriteria);
        System.out.println(auditCriteria);
        return new CollectionPage((int)auditPage.getTotalElements(), auditPage.getContent(), new Criteria());
    }




}