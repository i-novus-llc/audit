package ru.i_novus.ms.audit.rest;

import net.n2oapp.platform.test.autoconfigure.DefinePort;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import ru.i_novus.ms.audit.Application;
import ru.i_novus.ms.audit.config.BackendConfiguration;
import ru.i_novus.ms.audit.model.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "cxf.jaxrs.client.classes-scan=true",
                "cxf.jaxrs.client.classes-scan-packages=ru.i_novus.ms.audit.service.api",
                "cxf.jaxrs.client.address=http://localhost:${server.port}/${cxf.path}",
        })
@DefinePort
@Import(BackendConfiguration.class)
@Ignore
public class ApplicationTest {

    private static final String TEST = "RANOSDADAFAFAGAGSKENGkngskl;fhlksfgnhklsfgnhklsfgnhfgkls";
    private static UUID ID = UUID.fromString("9264b032-ff05-11e8-8eb2-f2801f1b9fd1");


    private static AuditForm auditRequest;

    @Autowired
    @Qualifier("auditServiceJaxRsProxyClient")
    private AuditRest auditService;

    @BeforeClass
    public static void initialize() {
        auditRequest = new AuditForm();
        auditRequest.setEventDate(LocalDateTime.now());
        auditRequest.setEventType("EventType");
        auditRequest.setObjectType("ObjectType");
        auditRequest.setObjectId("ObjectId");
        auditRequest.setObjectName("ObjectName");
        auditRequest.setUserId("UserId");
        auditRequest.setUsername("Username");
        auditRequest.setSourceApplication("SourceApplication");
        auditRequest.setSourceWorkstation("SourceWorkstation");
        auditRequest.setContext("{'field': 'name', 'value': 'Значение'}");
    }


    @Test
    public void testAdd() {
        Audit audit = auditService.add(auditRequest);
        assertNotNull(audit.getId());
        assertNotNull(audit.getCreationDate());
        assertAuditEquals(auditRequest, audit);
        ID = audit.getId();
    }

    @Test
    public void testGetById() {
        Audit auditById = auditService.getById(ID);
        assertEquals(ID, auditById.getId());
    }

    @Test
    public void testSearchByEventType() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setEventType(auditRequest.getEventType());
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setEventType(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByEventDate() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setEventDateFrom(auditRequest.getEventDate().minusDays(1));
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setEventDateFrom(auditRequest.getEventDate().plusDays(1));
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
        criteria.setEventDateFrom(null);

        criteria.setEventDateTo(auditRequest.getEventDate().plusDays(1));
        search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setEventDateTo(auditRequest.getEventDate().minusYears(1));
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
        criteria.setEventDateTo(null);
    }

    @Test
    public void testSearchByObjectId() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setObjectId(auditRequest.getObjectId());
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setObjectId(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByObjectName() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        AuditCriteria auditCriteria = new AuditCriteria(criteria);
        auditCriteria.setPageNumber(1);
        auditCriteria.setPageSize(10);
        criteria.setObjectName(new String[]{auditRequest.getObjectName()});
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setObjectName(new String[]{TEST});
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByObjectType() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setObjectType(new String[]{auditRequest.getObjectType()});
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setObjectType(new String[]{TEST});
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByUserId() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setUserId(auditRequest.getUserId());
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setUserId(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByUsername() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setUsername(auditRequest.getUsername());
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setUsername(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchBySourceApplication() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setSourceApplication(new String[]{auditRequest.getSourceApplication()});
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setSourceApplication(new String[]{TEST});
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchBySourceWorkstation() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setSourceApplication(new String[]{auditRequest.getSourceApplication()});
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setSourceWorkstation(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    @Test
    public void testSearchByContext() {
        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
        criteria.setContext(criteria.getContext());
        Page<Audit> search = auditService.search(criteria);
        assertTrue(search.getTotalElements() > 0);

        criteria.setContext(TEST);
        search = auditService.search(criteria);
        assertEquals(0, search.getTotalElements());
    }

    public static <T extends AbstractAudit> void assertAuditEquals(T expected, Audit actual) {
        assertEquals(expected.getEventDate(), actual.getEventDate());
        assertEquals(expected.getEventType(), actual.getEventType());
        assertEquals(expected.getObjectType(), actual.getObjectType());
        assertEquals(expected.getObjectId(), actual.getObjectId());
        assertEquals(expected.getObjectName(), actual.getObjectName());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getSourceApplication(), actual.getSourceApplication());
        assertEquals(expected.getSourceWorkstation(), actual.getSourceWorkstation());
        assertEquals(expected.getContext(), actual.getContext());
    }
}