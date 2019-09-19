package ru.i_novus.ms.audit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.i_novus.ms.audit.model.AuditForm;
import ru.i_novus.ms.audit.repository.AuditRepository;

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
    }

    @Test
    public void testCreateIntegration() {
        AuditForm auditForm = new AuditForm();
        auditForm.setSourceApplication(TEXT);
        auditForm.setSender(TEXT);
        auditForm.setReceiver(TEXT);
        doNothing().when(sourceApplicationService).createIfNotPresent(anyString());
        service.create(auditForm);
        verify(auditRepository, times(1)).save(any());
        verify(sourceApplicationService, times(3)).createIfNotPresent(any());
    }
}
