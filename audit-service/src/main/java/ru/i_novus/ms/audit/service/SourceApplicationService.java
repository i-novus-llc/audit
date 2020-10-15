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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.i_novus.ms.audit.builder.entity.AuditSourceApplicationEntityBuilder;
import ru.i_novus.ms.audit.builder.model.AuditSourceApplicationBuilder;
import ru.i_novus.ms.audit.criteria.AuditSourceApplicationCriteria;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;
import ru.i_novus.ms.audit.model.AuditSourceApplication;
import ru.i_novus.ms.audit.repository.AuditSourceApplicationRepository;

import java.util.Optional;

@Service
public class SourceApplicationService {

    @Autowired
    private AuditSourceApplicationRepository auditSourceApplicationRepository;

    public Page<AuditSourceApplication> search(AuditSourceApplicationCriteria criteria) {
        return auditSourceApplicationRepository.findAll(
                        QueryService.toPredicate(criteria),
                        PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), Sort.by(criteria.getDefaultOrders())))
                .map(AuditSourceApplicationBuilder::buildByEntity);
    }

    public void createIfNotPresent(String code) {
        Optional<AuditSourceApplicationEntity> optional = auditSourceApplicationRepository.findByCode(code);
        if (optional.isEmpty()) {
            auditSourceApplicationRepository.save(AuditSourceApplicationEntityBuilder.buildEntity(code));
        }
    }
}
