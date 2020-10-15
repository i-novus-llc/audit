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

package ru.i_novus.ms.audit.client.impl.converter;

import lombok.AccessLevel;
import lombok.Setter;
import net.n2oapp.platform.i18n.Messages;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.client.SourceApplicationAccessor;
import ru.i_novus.ms.audit.client.SourceWorkstationAccessor;
import ru.i_novus.ms.audit.client.UserAccessor;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.model.AuditForm;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@Setter(AccessLevel.PROTECTED)
public class RequestConverter {

    @Autowired(required = false)
    private UserAccessor userAccessor;

    @Autowired(required = false)
    private SourceWorkstationAccessor workstationAccessor;

    @Autowired(required = false)
    private SourceApplicationAccessor applicationAccessor;

    @Autowired
    private Messages messages;

    public AuditForm toAuditRequest(AuditClientRequest request) {

        AuditForm auditForm = convertAccessorFields(request);

        auditForm.setEventDate(request.getEventDate() == null ? LocalDateTime.now() : request.getEventDate());
        auditForm.setContext(request.getContext());
        auditForm.setAuditType(request.getAuditType());

        if (request.getEventType() != null)
            auditForm.setEventType(getMessage(request.getEventType().getValue(), request.getEventType().getArgs()));
        if (request.getObjectType() != null)
            auditForm.setObjectType(getMessage(request.getObjectType().getValue(), request.getObjectType().getArgs()));
        if (request.getObjectId() != null)
            auditForm.setObjectId(getMessage(request.getObjectId().getValue(), request.getObjectId().getArgs()));
        if (request.getObjectName() != null)
            auditForm.setObjectName(getMessage(request.getObjectName().getValue(), request.getObjectName().getArgs()));
        if (request.getHostname() != null)
            auditForm.setHostname(getMessage(request.getHostname().getValue(), request.getHostname().getArgs()));
        if (request.getSender() != null)
            auditForm.setSender(getMessage(request.getSender().getValue(), request.getSender().getArgs()));
        if (request.getReceiver() != null)
            auditForm.setReceiver(getMessage(request.getReceiver().getValue(), request.getReceiver().getArgs()));

        validateAuditForm(auditForm);

        return auditForm;
    }

    private AuditForm convertAccessorFields(AuditClientRequest request) {
        AuditForm auditForm = new AuditForm();

        if (request.getUserId() != null && request.getUserId().getValue() != null) {
            auditForm.setUserId(getMessage(request.getUserId().getValue(), request.getUserId().getArgs()));
        } else if (userAccessor != null && userAccessor.get() != null && userAccessor.get().getUserId() != null) {
            auditForm.setUserId(userAccessor.get().getUserId());
        }

        if (request.getUsername() != null && request.getUsername().getValue() != null) {
            auditForm.setUsername(getMessage(request.getUsername().getValue(), request.getUsername().getArgs()));
        } else if (userAccessor != null && userAccessor.get() != null && userAccessor.get().getUsername() != null) {
            auditForm.setUsername(userAccessor.get().getUsername());
        }

        if (request.getSourceApplication() != null && request.getSourceApplication().getValue() != null) {
            auditForm.setSourceApplication(getMessage(request.getSourceApplication().getValue(), request.getSourceApplication().getArgs()));
        } else if (applicationAccessor != null) {
            auditForm.setSourceApplication(applicationAccessor.get());
        }

        if (request.getSourceWorkstation() != null && request.getSourceWorkstation().getValue() != null) {
            auditForm.setSourceWorkstation(getMessage(request.getSourceWorkstation().getValue(), request.getSourceWorkstation().getArgs()));
        } else if (workstationAccessor != null) {
            auditForm.setSourceWorkstation(workstationAccessor.get());
        }

        return auditForm;
    }

    /**
     * Вспомогательный метод для прогона bean validations на форме аудита
     *
     * @param auditForm валидируемая форма аудита
     */
    private void validateAuditForm(AuditForm auditForm) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AuditForm>> constraintViolations = validator.validate(auditForm);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * Вспомогательный метод для обхода NPE при получении значения из messages
     *
     * @param code Код сообщения
     * @param args Параметры сообщения
     * @return Локализованное сообщение или null, если code был равен null
     */
    private String getMessage(String code, Object[] args) {
        if (code != null) {
            return messages.getMessage(code, args);
        } else {
            return null;
        }
    }
}
