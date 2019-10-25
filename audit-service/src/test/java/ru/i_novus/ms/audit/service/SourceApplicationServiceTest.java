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
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.model.AuditSourceApplication;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SourceApplicationServiceTest {

    private static final String TEXT = "test";

    @InjectMocks
    private SourceApplicationService service;

    @Mock
    private AuditSourceApplicationRepository repository;

    @Test
    public void testCreateIfNotPresent() {
        doReturn(Optional.of(new AuditObjectEntity())).when(repository).findByCode(anyString());
        service.createIfNotPresent(TEXT);
        verify(repository, never()).save(any());
    }

    @Test
    public void testCreateIfNotPresentEmpty() {
        doReturn(Optional.empty()).when(repository).findByCode(anyString());
        service.createIfNotPresent(TEXT);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void testSearchEmpty() {
        AuditSourceApplicationCriteria criteria = beforeSearch();
        doReturn(Page.empty()).when(repository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<AuditSourceApplication> page = service.search(criteria);

        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testSearch() {
        AuditSourceApplicationCriteria criteria = beforeSearch();
        List<AuditSourceApplicationEntity> entityList = Arrays.asList(
                new AuditSourceApplicationEntity(),
                new AuditSourceApplicationEntity(),
                new AuditSourceApplicationEntity()
        );

        doReturn(new PageImpl<>(entityList)).when(repository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        Page<AuditSourceApplication> page = service.search(criteria);

        assertEquals(3, page.getTotalElements());
    }

    private AuditSourceApplicationCriteria beforeSearch() {
        AuditSourceApplicationCriteria criteria = new AuditSourceApplicationCriteria();
        criteria.setCode("code");
        criteria.setPageSize(10);
        return criteria;
    }
}
