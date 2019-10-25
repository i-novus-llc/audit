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
import ru.i_novus.ms.audit.criteria.AuditEventTypeCriteria;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;
import ru.i_novus.ms.audit.repository.EventTypeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventTypeServiceTest {
    @Mock
    private EventTypeRepository repository;
    @InjectMocks
    private EventTypeService service;

    private static String NAME = "testName";
    private static short TYPE = 1;

    @Test
    public void testCreateIfNotPresentEmpty() {
        doReturn(Optional.empty()).when(repository).findByNameAndAuditTypeId(anyString(), anyShort());
        service.createIfNotPresent(NAME, TYPE);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void createIfNotPresent() {
        doReturn(Optional.of(new AuditEventTypeEntity())).when(repository).findByNameAndAuditTypeId(anyString(), anyShort());
        service.createIfNotPresent(NAME, TYPE);

        verify(repository, never()).save(any());
    }

    @Test
    public void testSearchEmpty() {
        AuditEventTypeCriteria criteria = beforeSearch();
        doReturn(Page.empty()).when(repository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<AuditEventType> page = service.search(criteria);

        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testSearch() {
        List<AuditEventTypeEntity> entityList = Arrays.asList(
                new AuditEventTypeEntity(),
                new AuditEventTypeEntity(),
                new AuditEventTypeEntity()
        );

        AuditEventTypeCriteria criteria = beforeSearch();
        doReturn(new PageImpl<>(entityList)).when(repository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<AuditEventType> page = service.search(criteria);

        assertEquals(3, page.getTotalElements());
    }

    private AuditEventTypeCriteria beforeSearch() {
        AuditEventTypeCriteria criteria = new AuditEventTypeCriteria();
        criteria.setAuditTypeId((short)1);
        criteria.setPageSize(10);
        return criteria;
    }
}
