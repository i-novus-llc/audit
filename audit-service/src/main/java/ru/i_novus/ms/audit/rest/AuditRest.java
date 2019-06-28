package ru.i_novus.ms.audit.rest;

import com.google.common.collect.Lists;
import net.n2oapp.criteria.api.CollectionPage;
import net.n2oapp.criteria.api.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.exception.NotFoundException;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditCriteriaDTO;
import ru.i_novus.ms.audit.model.AuditCriteria;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.service.AuditService;
import ru.i_novus.ms.audit.service.api.AuditControllerApi;

import java.util.*;

import static java.util.Objects.isNull;


@RestController
public class AuditRest implements AuditControllerApi {

    @Autowired
    private AuditService auditService;

    @Override
    @GetMapping("/audit/{id}")
    public Audit getById(@PathVariable UUID id) {
        Optional<AuditEntity> auditEntity = auditService.getById(id);
        if (auditEntity.isEmpty())
            throw new NotFoundException();

        return AuditService.getAuditByEntity(auditEntity.get());
    }

    @GetMapping("/auditN20/{id}")
    public AuditEntity getEntityById(@PathVariable UUID id) {
        Optional<AuditEntity> auditEntity = auditService.getById(id);
        return auditEntity.orElseThrow(NotFoundException::new);
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
    public CollectionPage<AuditEntity> getAudits(
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
//                                              @RequestParam Map<String,String> allRequestParams
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @RequestParam(value = "sortingColumn", required = false, defaultValue = "eventDate") String sortingColumn,
                                              @RequestParam(value = "sortingOrder", required = false, defaultValue = "desc") String sortingOrder,
                                              AuditCriteria auditCriteria
        ){
//        for(Map.Entry entry: allRequestParams.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        auditCriteria.setPageNumber(page);
        auditCriteria.setPageSize(size);
        System.out.println(sortingColumn + " " + sortingOrder + " ");
        if (sortingColumn!=null && sortingOrder!=null) {
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortingOrder), sortingColumn);
            auditCriteria.setOrders(Lists.newArrayList(order));
        }
        Page<AuditEntity> auditPage = auditService.searchEntity(auditCriteria);
        System.out.println(auditCriteria);
        return new CollectionPage((int)auditPage.getTotalElements(), auditPage.getContent(), new Criteria());
//        return null;
    }




}