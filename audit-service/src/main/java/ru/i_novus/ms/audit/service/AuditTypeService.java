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

package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditTypeEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditTypeBuilder;
import ru.i_novus.ms.audit.entity.AuditTypeEntity;
import ru.i_novus.ms.audit.enums.AuditTypeEnum;
import ru.i_novus.ms.audit.model.AuditType;
import ru.i_novus.ms.audit.repository.AuditTypeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public Collection<AuditType> getDefaultAuditTypes() {
        List<AuditTypeEntity> auditTypeEntities = auditTypeRepository.findAllById(
                Stream.of(AuditTypeEnum.values())
                        .map(AuditTypeEnum::getId)
                        .collect(Collectors.toList()));

        return auditTypeEntities.stream()
                .map(AuditTypeBuilder::buildByEntity)
                .collect(Collectors.toList());
    }

    public void createIfNotPresent(Short id) {
        Optional<AuditTypeEntity> auditTypeEntity = auditTypeRepository.findById(id);
        if (auditTypeEntity.isEmpty()) {
            auditTypeRepository.save(AuditTypeEntityBuilder.buildDefaultAuditType(id));
        }
    }
}
