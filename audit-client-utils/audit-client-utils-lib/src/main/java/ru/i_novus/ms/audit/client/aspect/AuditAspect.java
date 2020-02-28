package ru.i_novus.ms.audit.client.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import ru.i_novus.ms.audit.client.AuditClient;
import ru.i_novus.ms.audit.client.annotation.Audit;
import ru.i_novus.ms.audit.client.annotation.AuditIgnorable;
import ru.i_novus.ms.audit.client.annotation.AuditIgnore;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.security.context.ServerContext;
import ru.i_novus.ms.audit.client.security.context.UserContext;
import ru.i_novus.ms.audit.client.security.model.CurrentAuthUser;
import ru.i_novus.ms.audit.client.security.properties.AuditProperties;

import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Aspect
@Slf4j
@Order
public class AuditAspect {

    @Autowired
    private AuditClient safeKidsAuditClient;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;
    @Autowired
    private ObjectMapper mapper;

    private AuditProperties properties;

    private static final String UNKNOWN_USER = "UNKNOWN";
    private static final short AUDIT_TYPE_USER_ACTION = 1;

    public AuditAspect(AuditProperties properties) {
        this.properties = properties;
    }

    @AfterReturning(pointcut = "@annotation(ru.i_novus.ms.audit.client.annotation.Audit)", returning = "result")
    public void audit(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Audit audit = targetMethod.getAnnotation(Audit.class);
        if (audit != null) {
            audit(audit.action(), audit.object(), result);
        }
    }

    private void audit(String action, String object, Object result) {
        if (result instanceof Response) {
            try {
                result = ((Response) result).getHeaders().get("auditObject").get(0);
            } catch (Exception e) {
                log.error("Error get auditObject from Response", e);
            }
        }

        AuditClientRequest request = new AuditClientRequest();
        setUser(request);
        setAuditIgnore(result);
        setObject(request, object, result);

        request.setHostname(ServerContext.getServerName());
        request.setSourceWorkstation(ServerContext.getSourceWorkStation());
        try {
            request.setContext(mapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            log.error("Error set context", e);
            request.setContext(result.toString());
        }
        try {
            request.setEventType(messageSourceAccessor.getMessage(action));
        } catch (NoSuchMessageException e) {
            log.error("Error set eventType", e);
            request.setEventType(action);
        }
        request.setEventDate(LocalDateTime.now());
        request.setAuditType(AUDIT_TYPE_USER_ACTION);
        safeKidsAuditClient.add(request);
    }

    private void setAuditIgnore(Object object) {
        List<Field> fields = FieldUtils.getAllFieldsList(object.getClass());
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        List<Field> ignoreFields = FieldUtils.getFieldsListWithAnnotation(object.getClass(), AuditIgnore.class);
        if (!CollectionUtils.isEmpty(ignoreFields)) {
            ignoreFields.forEach(field -> {
                try {
                    FieldUtils.writeField(field, object, null, true);
                } catch (Exception e) {
                    log.error("Error set null to AuditIgnore field", e);
                }
            });
        }

        checkAuditIgnorableFields(object, ignoreFields);
    }

    private void checkAuditIgnorableFields(Object object, List<Field> ignoreFields) {
        List<Field> ignorableFields = FieldUtils.getFieldsListWithAnnotation(object.getClass(), AuditIgnorable.class);
        ignorableFields.removeAll(ignoreFields);
        if (!CollectionUtils.isEmpty(ignorableFields)) {
            ignorableFields.forEach(field -> {
                try {
                    if (TypeUtils.isAssignable(field.getType(), Collection.class)) {
                        Collection<Object> collection = (Collection<Object>) FieldUtils.readField(field, object, true);
                        collection.forEach(this::setAuditIgnore);
                    } else if (field.getType().isArray()) {
                        Object array = FieldUtils.readField(field, object, true);
                        int length = Array.getLength(array);
                        for (int i = 0; i < length; i++) {
                            setAuditIgnore(Array.get(array, i));
                        }
                    } else {
                        setAuditIgnore(FieldUtils.readField(field, object, true));
                    }
                } catch (Exception e) {
                    log.error("Error in AuditIgnorable annotation scanning", e);
                }
            });
        }
    }

    private void setUser(AuditClientRequest request) {
        CurrentAuthUser authUser = UserContext.getAuthUser();
        if (authUser != null) {
            request.setUserId(authUser.getUserId());
            request.setUsername(authUser.getUsername());
        } else {
            request.setUserId(UNKNOWN_USER);
            request.setUsername(UNKNOWN_USER);
        }
    }

    private void setObject(AuditClientRequest request, String object, Object result) {
        String objectName;
        if (StringUtils.isNotEmpty(object)) {
            request.setObjectType(object);
            objectName = properties.getObject().get(object);
        } else {
            request.setObjectType(result.getClass().getSimpleName());
            objectName = properties.getObject().get(result.getClass().getSimpleName());
        }
        request.setObjectName(StringUtils.isNotEmpty(objectName) ? objectName : result.getClass().getSimpleName());

        try {
            request.setObjectId(result.getClass().getMethod("getId").invoke(result).toString());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("Error set objectId", e);
            request.setObjectId(result.getClass().getSimpleName());
        }
    }

}
