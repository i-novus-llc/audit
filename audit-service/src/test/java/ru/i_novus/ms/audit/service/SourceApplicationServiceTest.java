package ru.i_novus.ms.audit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Optional;

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
}
