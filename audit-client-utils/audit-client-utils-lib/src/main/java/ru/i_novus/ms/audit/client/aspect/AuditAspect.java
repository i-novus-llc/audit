package ru.i_novus.ms.audit.client.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import org.springframework.data.domain.Page;
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

/**
 * Аспект для обработки ответов методов с аннотацией {@link Audit} и отправки их через клиент аудита
 */
@Aspect
@Slf4j
@Order
public class AuditAspect {

    @Autowired
    private AuditClient auditClient;
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

    /**
     * Обработка ответа метода с аннотацией @Audit и отправка через клиент аудита
     *
     * @param joinPoint joinPoint вызова аспекта
     * @param result    объект возвращаемый методом с аннотацией {@link Audit}
     */
    @AfterReturning(pointcut = "@annotation(ru.i_novus.ms.audit.client.annotation.Audit)", returning = "result")
    public void audit(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Audit audit = targetMethod.getAnnotation(Audit.class);
        if (audit != null && result != null) {
            audit(audit.action(), audit.object(), result);
        }
    }

    /**
     * Обработка ответа и отправка через клиент аудита
     *
     * @param action параметр action из аннотации {@link Audit}. Используется для заполнения поля eventType аудита.
     * @param object параметр object из аннотации {@link Audit}. Используется для заполнения полей objectType, objectName аудита.
     * @param result объект возвращаемый методом с аннотацией {@link Audit}
     */
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
        setObject(request, object, result);

        request.setHostname(ServerContext.getServerName());
        request.setSourceWorkstation(ServerContext.getSourceWorkStation());
        request.setContext(getAuditContext(result));
        try {
            request.setEventType(messageSourceAccessor.getMessage(action));
        } catch (NoSuchMessageException e) {
            log.error("Error set eventType", e);
            request.setEventType(action);
        }
        request.setEventDate(LocalDateTime.now());
        request.setAuditType(AUDIT_TYPE_USER_ACTION);
        auditClient.add(request);
    }

    /**
     * Формирование контекста аудита.
     *
     * @param result объект возвращаемый методом с аннотацией {@link Audit}
     * @return строка в json формате если объект корректно обрабатывается через ObjectMapper или
     * результат {@link Object#toString()} в ином случае.
     */
    private String getAuditContext(Object result) {
        JsonNode resultNode;

        if (result instanceof Page) {
            resultNode = getCollectionContext(((Page<Object>) result).getContent());
        } else if (result instanceof Collection) {
            resultNode = getCollectionContext((Collection<Object>) result);
        } else {
            resultNode = getObjectContext(result);
        }

        try {
            return mapper.writeValueAsString(resultNode);
        } catch (JsonProcessingException e) {
            log.error("Error set context", e);
            return result.toString();
        }
    }

    /**
     * Формирование контекста из {@link Collection}
     *
     * @param collection аудируемая коллекция
     * @return нода для заполнения контекста аудита
     */
    private ArrayNode getCollectionContext(Collection<Object> collection) {
        ArrayNode collectionNode = mapper.createArrayNode();
        if (collection != null) {
            collection.forEach(o -> collectionNode.add(getObjectContext(o)));
        }

        return collectionNode;
    }


    /**
     * Формирование контекста из {@link Object}
     *
     * @param result аудируемый объект
     * @return нода для заполнения контекста аудита
     */
    private JsonNode getObjectContext(Object result) {
        List<Field> fields = FieldUtils.getAllFieldsList(result.getClass());
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        JsonNode objectNode = mapper.valueToTree(result);
        if (objectNode.isObject()) {
            checkAuditIgnore((ObjectNode) objectNode, result);
        }

        return objectNode;
    }

    /**
     * Удаление нод из json соответствующих полям с аннотацией {@link AuditIgnore}
     *
     * @param jsonNode JsonNode соответствующий обрабатываему объекту
     * @param result   обрабатываемый объект
     */
    private void checkAuditIgnore(ObjectNode jsonNode, Object result) {
        List<Field> ignoreFields = FieldUtils.getFieldsListWithAnnotation(result.getClass(), AuditIgnore.class);
        if (!CollectionUtils.isEmpty(ignoreFields)) {
            ignoreFields.forEach(field -> jsonNode.remove(field.getName()));
        }

        checkAuditIgnorable(jsonNode, result);
    }

    /**
     * Нахождение полей с аннотацией {@link AuditIgnorable} и дальнейший вызов {@link #checkAuditIgnore(ObjectNode, Object)}
     *
     * @param jsonNode JsonNode соответствующий обрабатываему объекту
     * @param result   обрабатываемый объект
     */
    private void checkAuditIgnorable(ObjectNode jsonNode, Object result) {
        List<Field> ignorableFields = FieldUtils.getFieldsListWithAnnotation(result.getClass(), AuditIgnorable.class);
        if (!CollectionUtils.isEmpty(ignorableFields)) {
            ignorableFields.forEach(field -> {
                try {
                    JsonNode fieldJson = jsonNode.get(field.getName());
                    if (TypeUtils.isAssignable(field.getType(), Collection.class)) {
                        checkAuditIgnoreCollection(jsonNode, result, field);
                    } else if (field.getType().isArray()) {
                        checkAuditIgnoreArray(fieldJson, result, field);
                    } else {
                        Object fieldObject = FieldUtils.readField(field, result, true);
                        if (fieldJson.isObject()) {
                            checkAuditIgnore((ObjectNode) fieldJson, fieldObject);
                        } else if (!fieldJson.isNull()) {
                            log.info(result.getClass().getName() + ": AuditIgnorable annotation scanning." +
                                    "Probably @AuditIgnorable used on primitive field.");
                        }
                    }
                } catch (IllegalAccessException e) {
                    log.error("Error in AuditIgnorable annotation scanning", e);
                }
            });
        }
    }

    /**
     * Нахождение полей с аннотацией {@link AuditIgnore} в коллекции
     *
     * @param jsonNode        JsonNode объекта в котором находится коллекция
     * @param result          объект в котором находится коллекция
     * @param collectionField поле result объекта в котором находится коллекция
     * @throws IllegalAccessException Если поле недоступно из-за области видимости. В данном методе это исключение не должно выбрасываться.
     */
    private void checkAuditIgnoreCollection(ObjectNode jsonNode, Object result, Field collectionField)
            throws IllegalAccessException {
        Collection<Object> collection = (Collection<Object>) FieldUtils.readField(collectionField, result, true);
        if (collection != null) {
            ArrayNode collectionNode = mapper.createArrayNode();
            collection.forEach(object -> {
                ObjectNode objectNode = mapper.valueToTree(object);
                checkAuditIgnore(objectNode, object);
                collectionNode.add(objectNode);
            });
            jsonNode.replace(collectionField.getName(), collectionNode);
        }
    }

    /**
     * Нахождение полей с аннотацией {@link AuditIgnore} в массиве
     *
     * @param arrayJson JsonNode массива
     * @param result    объект в котором находится массив
     * @param field     поле result объекта в котором находится массив
     * @throws IllegalAccessException Если поле недоступно из-за области видимости. В данном методе это исключение не должно выбрасываться.
     */
    private void checkAuditIgnoreArray(JsonNode arrayJson, Object result, Field field)
            throws IllegalAccessException {
        Object array = FieldUtils.readField(field, result, true);
        if (array != null) {
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                Object object = Array.get(array, i);
                JsonNode objectNode = arrayJson.get(i);
                if (objectNode.isObject()) {
                    checkAuditIgnore((ObjectNode) objectNode, object);
                } else if (!objectNode.isNull()) {
                    log.info(result.getClass().getName() + ": AuditIgnorable annotation scanning." +
                            "Probably @AuditIgnorable used on primitive field.");
                }
            }
        }
    }

    /**
     * Установка userId и username в {@link AuditClientRequest}
     *
     * @param request изменяемый {@link AuditClientRequest}
     */
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

    /**
     * Устновка objectType и objectId в {@link AuditClientRequest}
     *
     * @param request изменяемый {@link AuditClientRequest}
     * @param object  параметр object из аннотации {@link Audit}
     * @param result  объект используемый для заполнения контекста
     */
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
            log.info("Object doesn't have id field getter", e);
            request.setObjectId(result.getClass().getSimpleName());
        }
    }
}
