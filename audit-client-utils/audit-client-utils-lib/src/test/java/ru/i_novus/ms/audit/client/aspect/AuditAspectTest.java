package ru.i_novus.ms.audit.client.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestPage;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceAccessor;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.annotation.Audit;
import ru.i_novus.ms.audit.client.annotation.AuditIgnorable;
import ru.i_novus.ms.audit.client.annotation.AuditIgnore;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.security.properties.AuditProperties;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuditAspectTest {

    @Mock
    private static JoinPoint mockedJoinPoint;
    @Mock
    private static MethodSignature mockedMethodSignature;
    @Mock
    private static AuditProperties mockedAuditProperties;
    @Spy
    private static ObjectMapper mockedObjectMapper;
    @Mock
    private static AuditClient mockedAuditClient;
    @Mock
    private static MessageSourceAccessor mockedMessageSourceAccessor;

    @InjectMocks
    private static AuditAspect auditAspect = new AuditAspect(mockedAuditProperties);

    ArgumentCaptor<AuditClientRequest> captor;

    private static TestedModelExtended testedModel;
    private static RestPage<TestedModelExtended> testedPage;
    private static List<TestedModelExtended> testedList;
    private static String testedString;

    private static final String EXPECTED_MODEL_JSON = "{\"id\":1,\"object\":{\"id\":1}," +
            "\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}";
    private static final String EXPECTED_EMPTY_MODEL_JSON = "{\"id\":1,\"object\":null,\"array\":null,\"list\":null}";
    private static final String EXPECTED_EMPTY_COLLECTION_ARRAY_MODEL_JSON = "{\"id\":1,\"object\":null,\"array\":[],\"list\":[]}";
    private static final String EXPECTED_PAGE_LIST_MODEL_JSON =
            "[{\"id\":1,\"object\":{\"id\":1},\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}," +
                    "{\"id\":1,\"object\":{\"id\":1},\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}]";
    private static final String EXPECTED_EMPTY_PAGE_LIST_MODEL_JSON = "[]";
    private static final String EXPECTED_STRING_JSON = "\"Tested string\"";

    @Before
    public void beforeEach() {
        when(mockedJoinPoint.getSignature()).thenReturn(mockedMethodSignature);
        when(mockedMethodSignature.getMethod())
                .thenReturn(MethodUtils.getMatchingMethod(TestedClass.class, "auditedMethod"));
        captor = ArgumentCaptor.forClass(AuditClientRequest.class);

        TestedObject testedObject1 = new TestedObject(1, 11);
        TestedObject testedObject2 = new TestedObject(2, 22);

        testedModel = new TestedModelExtended();
        testedModel.setId(1);
        testedModel.setIgnoredString("test");
        testedModel.setObject(testedObject1);
        testedModel.setArray(Arrays.array(testedObject1, testedObject2));
        testedModel.setList(List.of(testedObject1, testedObject2));

        testedPage = new RestPage<>(List.of(testedModel, testedModel));
        testedList = List.of(testedModel, testedModel);
        testedString = "Tested string";
    }

    @Test
    public void auditSuccessful() {
        auditAspect.audit(mockedJoinPoint, testedModel);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void auditEmptyModelSuccessful() {
        testedModel = new TestedModelExtended();
        testedModel.setId(1);

        auditAspect.audit(mockedJoinPoint, testedModel);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_EMPTY_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void auditEmptyCollectionArrayModelSuccessful() {
        testedModel = new TestedModelExtended();
        testedModel.setId(1);
        testedModel.setArray(Arrays.array());
        testedModel.setList(List.of());

        auditAspect.audit(mockedJoinPoint, testedModel);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_EMPTY_COLLECTION_ARRAY_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditPageSuccessful() {
        auditAspect.audit(mockedJoinPoint, testedPage);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_PAGE_LIST_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditListSuccessful() {
        auditAspect.audit(mockedJoinPoint, testedList);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_PAGE_LIST_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditEmptyPageSuccessful() {
        testedPage = new RestPage<>();

        auditAspect.audit(mockedJoinPoint, testedPage);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_EMPTY_PAGE_LIST_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditEmptyListSuccessful() {
        testedList = List.of();

        auditAspect.audit(mockedJoinPoint, testedList);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_EMPTY_PAGE_LIST_MODEL_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditStringSuccessful() {
        auditAspect.audit(mockedJoinPoint, testedString);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_STRING_JSON, captor.getValue().getContext());
    }
}

class TestedClass {
    @Audit(object = "object", action = "action")
    public void auditedMethod() {
        //for annotation testing, do nothing
    }
}

@Getter
@Setter
class TestedModel {
    private Integer id;

    @AuditIgnore
    private String ignoredString;

    @AuditIgnorable
    private TestedObject object;
}

@Getter
@Setter
class TestedModelExtended extends TestedModel {
    @AuditIgnorable
    private TestedObject[] array;

    @AuditIgnorable
    List<TestedObject> list;
}

@Getter
@Setter
@AllArgsConstructor
class TestedObject {
    private Integer id;

    @AuditIgnore
    private int ignoredInt;
}