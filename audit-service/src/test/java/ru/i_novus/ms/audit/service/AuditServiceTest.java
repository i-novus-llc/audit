package ru.i_novus.ms.audit.service;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceTest {

    private static final String TEXT = "test";

    @InjectMocks
    private AuditService service;
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private AuditObjectService auditObjectService;
    @Mock
    private SourceApplicationService sourceApplicationService;

    @Test
    public void testCreate() {
        AuditForm auditForm = new AuditForm();
        service.create(auditForm);
        verify(auditRepository, times(1)).save(any());
        verify(auditObjectService, never()).createIfNotPresent(anyString(), anyString());
        verify(sourceApplicationService, never()).createIfNotPresent(anyString());
    }

    @Test
    public void testCreateIntegration() {
        AuditForm auditForm = new AuditForm();
        auditForm.setSourceApplication(TEXT);
        auditForm.setSender(TEXT);
        auditForm.setReceiver(TEXT);
        auditForm.setObjectType(TEXT);
        auditForm.setObjectName(TEXT);
        doNothing().when(sourceApplicationService).createIfNotPresent(anyString());
        doNothing().when(auditObjectService).createIfNotPresent(anyString(), anyString());
        service.create(auditForm);
        verify(auditRepository, times(1)).save(any());
        verify(auditObjectService,  times(1)).createIfNotPresent(anyString(), anyString());
        verify(sourceApplicationService, times(3)).createIfNotPresent(any());
    }

    @Test
    public void testGetLastAuditEmpty() {
        doReturn(Optional.empty())
                .when(auditRepository).findFirstByAuditTypeIdAndAuditSourceApplicationOrderByEventDateDesc(anyShort(), anyString());

        Audit audit = service.getLastAudit((short) 1, "access");

        assertNull(audit);
    }

    @Test
    public void testGetLastAudit() {
        AuditEntity entity = AuditEntity.builder().build();
        doReturn(Optional.of(entity))
                .when(auditRepository).findFirstByAuditTypeIdAndAuditSourceApplicationOrderByEventDateDesc(anyShort(), anyString());

        Audit audit = service.getLastAudit((short) 1, "access");

        assertNotNull(audit);
    }

    @Test
    public void testGetByIdEmpty() {
        doReturn(Optional.empty()).when(auditRepository).searchEntityBylastMonth(any());
        doReturn(Optional.empty()).when(auditRepository).findById(any());
        Optional<AuditEntity> entity = service.getById(UUID.randomUUID());

        assertEquals(entity, Optional.empty());
        verify(auditRepository, times(1)).findById(any());
    }

    @Test
    public void testGetById() {
        doReturn(Optional.of(AuditEntity.class)).when(auditRepository).searchEntityBylastMonth(any());
        Optional<AuditEntity> entity = service.getById(UUID.randomUUID());

        assertNotEquals(entity, Optional.empty());
        verify(auditRepository, never()).findById(any());
    }

    @Test
    public void testSearchEmpty() {
        doReturn(Page.empty()).when(auditRepository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<Audit> auditPage = service.search(new AuditCriteria());

        assertEquals(Page.empty(), auditPage);
    }

    @Test
    public void testSearch() {
        AuditCriteria criteria = new AuditCriteria();
        criteria.setPageSize(10);
        List<AuditEntity> entityList = Arrays.asList(
                new AuditEntity(),
                new AuditEntity(),
                new AuditEntity()
        );

        doReturn(new PageImpl<>(entityList)).when(auditRepository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<Audit> page = service.search(criteria);

        assertEquals(3, page.getTotalElements());
    }
}
