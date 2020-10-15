/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.client.aspect;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceAccessor;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.annotation.Audit;
import ru.i_novus.ms.audit.client.annotation.AuditIgnorable;
import ru.i_novus.ms.audit.client.annotation.AuditIgnore;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.security.properties.AuditProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private static final String IGNORED_STRING = "test";
    private static final String EXPECTED_MODEL_JSON = "{\"id\":1,\"object\":{\"id\":1}," +
            "\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}";
    private static final String EXPECTED_EMPTY_MODEL_JSON = "{\"id\":1}";
    private static final String EXPECTED_EMPTY_COLLECTION_ARRAY_MODEL_JSON = "{\"id\":1,\"array\":[],\"list\":[]}";
    private static final String EXPECTED_PAGE_LIST_MODEL_JSON =
            "[{\"id\":1,\"object\":{\"id\":1},\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}," +
                    "{\"id\":1,\"object\":{\"id\":1},\"array\":[{\"id\":1},{\"id\":2}],\"list\":[{\"id\":1},{\"id\":2}]}]";
    private static final String EXPECTED_EMPTY_PAGE_LIST_MODEL_JSON = "[]";
    private static final String EXPECTED_STRING_JSON = "\"Tested string\"";
    private static final String EXPECTED_MODEL_WITH_DATES_JSON = "{\"id\":1,\"date\":\"2020-05\"," +
            "\"time\":\"12:30:50.000000045\",\"dateTime\":\"2020-05-10T12:30:35.00000004\"}";

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
        testedModel.setIgnoredString(IGNORED_STRING);
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

    @Test
    public void AuditWithDatesSuccessful() {
        testedModel = new TestedModelExtended();
        testedModel.setId(1);
        testedModel.setDate(LocalDate.of(2020, 5, 10));
        testedModel.setTime(LocalTime.of(12, 30, 50, 45));
        testedModel.setDateTime(LocalDateTime.of(2020, 5, 10, 12, 30, 35, 40));

        auditAspect.audit(mockedJoinPoint, testedModel);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(EXPECTED_MODEL_WITH_DATES_JSON, captor.getValue().getContext());
    }

    @Test
    public void AuditObjectIdMethodSuccessful() {
        when(mockedMethodSignature.getMethod())
                .thenReturn(MethodUtils.getMatchingMethod(TestedClass.class, "auditedObjectIdMethod"));

        auditAspect.audit(mockedJoinPoint, testedModel);
        verify(mockedAuditClient).add(captor.capture());

        assertEquals(IGNORED_STRING, captor.getValue().getObjectId().getValue());
    }
}

class TestedClass {
    @Audit(object = "object", action = "action")
    public void auditedMethod() {
        //for annotation testing, do nothing
    }

    @Audit(object = "object", action = "action", objectIdMethod = "getIgnoredString")
    public void auditedObjectIdMethod() {
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

    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate date;

    private LocalTime time;

    private LocalDateTime dateTime;
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