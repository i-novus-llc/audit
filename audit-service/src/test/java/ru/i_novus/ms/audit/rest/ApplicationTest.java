//package ru.i_novus.ms.audit.rest;
//
//import net.n2oapp.platform.test.autoconfigure.DefinePort;
//import net.n2oapp.platform.test.autoconfigure.EnableEmbeddedPg;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.i_novus.ms.audit.Application;
//import ru.i_novus.ms.audit.config.BackendConfiguration;
//import ru.i_novus.ms.audit.model.AbstractAudit;
//import ru.i_novus.ms.audit.entity.AuditEntity;
//import ru.i_novus.ms.audit.model.AuditCriteriaDTO;
//import ru.i_novus.ms.audit.model.AuditForm;
//import ru.i_novus.ms.audit.service.api.AuditControllerApi;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        classes = Application.class,
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
//        properties = {
//                "cxf.jaxrs.client.classes-scan=true",
//                "cxf.jaxrs.client.classes-scan-packages=ru.i_novus.ms.audit.service.api",
//                "cxf.jaxrs.client.address=http://localhost:${server.port}/${cxf.path}"
//        })
//@DefinePort
//@EnableEmbeddedPg
//@Import(BackendConfiguration.class)
//public class ApplicationTest {
//
//    private static final String TEST = "test";
//    private static final UUID ID = UUID.fromString("9264b032-ff05-11e8-8eb2-f2801f1b9fd1");
//
//    private static AuditForm auditRequest;
//
//    @Autowired
//    @Qualifier("auditServiceJaxRsProxyClient")
//    private AuditControllerApi auditService;
//
//    @BeforeClass
//    public static void initialize() {
//        auditRequest = new AuditForm();
//        auditRequest.setEventDate(LocalDateTime.now().withNano(0));
//        auditRequest.setEventType("EventType");
//        auditRequest.setAuditObjectTypes("ObjectType");
//        auditRequest.setAuditObjectId("ObjectId");
//        auditRequest.setAuditObjectNames("ObjectName");
//        auditRequest.setUserId("UserId");
//        auditRequest.setUsername("Username");
//        auditRequest.setAuditSourceApplication("SourceApplication");
//        auditRequest.setAuditSourceWorkstations("SourceWorkstation");
//        auditRequest.setContext("{\"field\": \"name\", \"value\": \"Значение\"}");
//    }
//
//    @Test
//    public void testAdd() {
//        AuditEntity audit = auditService.add(auditRequest);
//        assertNotNull(audit.getId());
//        assertNotNull(audit.getCreationDate());
//        assertAuditEquals(auditRequest, audit);
//    }
//
//    @Test
//    public void testGetById() {
//        AuditEntity auditById = auditService.getById(ID);
//        assertEquals(ID, auditById.getId());
//    }
//
//    @Test
//    public void testSearchByEventType() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setEventType(auditRequest.getEventType());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setEventType(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByEventDate() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setEventDateFrom(auditRequest.getEventDate().minusDays(1));
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setEventDateFrom(auditRequest.getEventDate().plusDays(1));
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//        criteria.setEventDateFrom(null);
//
//        criteria.setEventDateTo(auditRequest.getEventDate().plusDays(1));
//        search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setEventDateTo(auditRequest.getEventDate().minusDays(1));
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//        criteria.setEventDateTo(null);
//    }
//
//    @Test
//    public void testSearchByObjectId() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setAuditObjectId(auditRequest.getAuditObjectId());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setAuditObjectId(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByObjectName() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setAuditObjectNames(auditRequest.getAuditObjectNames());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setAuditObjectNames(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByObjectType() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setAuditObjectTypes(auditRequest.getAuditObjectTypes());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setAuditObjectTypes(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByUserId() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setUserId(auditRequest.getUserId());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setUserId(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByUsername() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//
//        criteria.setUsername(auditRequest.getUsername());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setUsername(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchBySourceApplication() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//
//        criteria.setAuditSourceApplication(auditRequest.getAuditSourceApplication());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setAuditSourceApplication(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchBySourceWorkstation() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setAuditSourceWorkstations(auditRequest.getAuditSourceWorkstations());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setAuditSourceWorkstations(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    @Test
//    public void testSearchByContext() {
//        AuditCriteriaDTO criteria = new AuditCriteriaDTO();
//        criteria.setContext(auditRequest.getContext());
//        Page<AuditEntity> search = auditService.search(criteria);
//        assertTrue(search.getTotalElements() > 0);
//
//        criteria.setContext(TEST);
//        search = auditService.search(criteria);
//        assertEquals(0, search.getTotalElements());
//    }
//
//    public static <T extends AbstractAudit> void assertAuditEquals(T expected, AuditEntity actual) {
//        assertEquals(expected.getEventDate(), actual.getEventDate());
//        assertEquals(expected.getEventType(), actual.getEventType());
//        assertEquals(expected.getAuditObjectTypes(), actual.getAuditObjectTypes());
//        assertEquals(expected.getAuditObjectId(), actual.getAuditObjectId());
//        assertEquals(expected.getAuditObjectNames(), actual.getAuditObjectNames());
//        assertEquals(expected.getUserId(), actual.getUserId());
//        assertEquals(expected.getUsername(), actual.getUsername());
//        assertEquals(expected.getAuditSourceApplication(), actual.getAuditSourceApplication());
//        assertEquals(expected.getAuditSourceWorkstations(), actual.getAuditSourceWorkstations());
//        assertEquals(expected.getContext(), actual.getContext());
//    }
//}