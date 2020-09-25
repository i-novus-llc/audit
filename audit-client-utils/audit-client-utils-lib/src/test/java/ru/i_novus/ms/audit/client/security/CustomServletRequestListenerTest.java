package ru.i_novus.ms.audit.client.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.web.SpringBootMockServletContext;
import ru.i_novus.ms.audit.client.security.context.ServerContext;
import ru.i_novus.ms.audit.client.security.context.UserContext;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class CustomServletRequestListenerTest {
    @InjectMocks
    private CustomServletRequestListener listener;
    @Mock
    private HttpServletRequest mockedRequest;

    private static final String TEST_ADDR = "testAddr";
    private static final String USER_ID = "UserId";
    private static final String USERNAME = "username";
    private static final String ORG_CODE ="orgСode";
    private static final String TEST_SERVER_NAME = "testServerName";
    private static ServletRequestEvent requestEvent;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void init() {
        SpringBootMockServletContext serverContext = new SpringBootMockServletContext("/");
        requestEvent = new ServletRequestEvent(serverContext, mockedRequest);
    }

    @After
    public void after() {
        UserContext.removeAuthUser();
        ServerContext.removeServerName();
        ServerContext.removeSourceWorkstation();
    }

    @Test
    public void requestDestroyedTest() {
        listener.requestDestroyed(requestEvent);
        assertNull(UserContext.getAuthUser());
        assertNull(ServerContext.getSourceWorkStation());
        assertNull(ServerContext.getServerName());
    }

    @Test
    public void requestInitializedTest() {
        doReturn(null).when(mockedRequest).getHeader(anyString());
        doReturn(TEST_ADDR).when(mockedRequest).getRemoteAddr();
        doReturn(TEST_SERVER_NAME).when(mockedRequest).getServerName();
        listener.requestInitialized(requestEvent);
        assertNull(UserContext.getAuthUser());
        assertEquals(TEST_ADDR, ServerContext.getSourceWorkStation());
        assertEquals(TEST_SERVER_NAME, ServerContext.getServerName());
    }

    @Test
    public void requestInitializedEmptyTest() {
        ServletRequestEvent requestEvent = Mockito.mock(ServletRequestEvent.class);
        listener.requestInitialized(requestEvent);
        assertNull(UserContext.getAuthUser());
        assertNull(ServerContext.getSourceWorkStation());
        assertNull(ServerContext.getServerName());
    }

    @Test
    public void requestInitializedWithUserTest() {
        doReturn(encodeUser(buildCurrentAuthUser(USER_ID, USERNAME, ORG_CODE))).when(mockedRequest).getHeader("userHeader");
        listener.requestInitialized(requestEvent);
        assertNotNull(UserContext.getAuthUser());
        assertEquals(USER_ID, UserContext.getAuthUser().getUserId());
        assertEquals(USERNAME, UserContext.getAuthUser().getUsername());
        assertEquals(ORG_CODE, UserContext.getAuthUser().getOrgCode());
    }

    @Test
    public void requestInitializedWithUserAuthorizationTest() {

        doReturn("Bearer blablabla." + encodeUser(buildCurrentAuthUser(null, USERNAME, null)))
                .when(mockedRequest).getHeader("Authorization");
        listener.requestInitialized(requestEvent);
        assertNotNull(UserContext.getAuthUser());
        assertNull(UserContext.getAuthUser().getUserId());
        assertEquals(USERNAME, UserContext.getAuthUser().getUsername());
    }

    @Test
    public void requestInitializedWithUserAuthTest() {

        doReturn("Bearer blablabla." + encodeUser(buildCurrentAuthUser(USER_ID, null, null)))
                .when(mockedRequest).getHeader("Authorization");
        listener.requestInitialized(requestEvent);
        assertNotNull(UserContext.getAuthUser());
        assertNull(UserContext.getAuthUser().getUsername());
        assertEquals(USER_ID, UserContext.getAuthUser().getUserId());
    }

    @Test
    public void requestInitializedWithOrgCodeTest() {

        doReturn("Bearer blablabla." + encodeUser(buildCurrentAuthUser(null, null, ORG_CODE)))
                .when(mockedRequest).getHeader("Authorization");
        listener.requestInitialized(requestEvent);
        assertNotNull(UserContext.getAuthUser());
        assertNull(UserContext.getAuthUser().getUsername());
        assertEquals(ORG_CODE, UserContext.getAuthUser().getOrgCode());
    }

    @Test
    public void requestInitializedEmptyUserTest() {
        doReturn("Bearer " + encodeEmptyUser()).when(mockedRequest).getHeader("Authorization");
        doReturn(TEST_ADDR).when(mockedRequest).getHeader("sourceWorkStationHeader");
        listener.requestInitialized(requestEvent);
        assertEquals(TEST_ADDR, ServerContext.getSourceWorkStation());
        assertNull(UserContext.getAuthUser());
    }

    private String encodeEmptyUser() {
        CurrentAuthUser userModel = CurrentAuthUser.builder().build();

        return encodeUser(userModel);
    }

    private String encodeUser(CurrentAuthUser userModel) {
        try {
            return Base64.getEncoder().encodeToString(MAPPER.writeValueAsString(userModel)
                    .getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            log.error("Error convert user to string", e);
        }

        return null;
    }

    private CurrentAuthUser buildCurrentAuthUser(String id, String name, String orgCode) {
        return CurrentAuthUser.builder().userId(id).username(name).orgCode(orgCode).build();
    }
}
