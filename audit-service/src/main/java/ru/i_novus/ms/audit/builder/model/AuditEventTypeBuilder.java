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

package ru.i_novus.ms.audit.builder.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.entity.AuditEventTypeEntity;
import ru.i_novus.ms.audit.model.AuditEventType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuditEventTypeBuilder {

    public static AuditEventType buildByEntity(AuditEventTypeEntity entity) {
        return  AuditEventType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
