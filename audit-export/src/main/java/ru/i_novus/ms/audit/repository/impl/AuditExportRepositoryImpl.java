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

package ru.i_novus.ms.audit.repository.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import ru.i_novus.ms.audit.criteria.AuditCriteria;
import ru.i_novus.ms.audit.entity.AuditEntity;
import ru.i_novus.ms.audit.properties.QueryProperties;
import ru.i_novus.ms.audit.repository.AuditExportRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
@EnableConfigurationProperties(QueryProperties.class)
public class AuditExportRepositoryImpl implements AuditExportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QueryProperties queryProperties;

    private Predicate[] toPredicate(AuditCriteria auditCriteria, CriteriaBuilder criteriaBuilder,
                                    Root<AuditEntity> auditEntityRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (auditCriteria.getId() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("id"), UUID.fromString(auditCriteria.getId())));
        }
        if (auditCriteria.getEventDateFrom() != null && auditCriteria.getEventDateTo() != null) {
            predicates.add(criteriaBuilder.between(auditEntityRoot.get("eventDate"),
                    auditCriteria.getEventDateFrom(), auditCriteria.getEventDateTo()));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getAuditEventType())) {
            predicates.add(auditEntityRoot.get("eventType").in(Arrays.asList(auditCriteria.getAuditEventType())));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getObjectType())) {
            predicates.add(auditEntityRoot.get("auditObjectType").in(Arrays.asList(auditCriteria.getObjectType())));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getObjectName())) {
            predicates.add(auditEntityRoot.get("auditObjectName").in(Arrays.asList(auditCriteria.getObjectName())));
        }
        if (auditCriteria.getObjectId() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(auditEntityRoot.get("objectId")),
                    String.format("%%%s%%", auditCriteria.getObjectId().trim().toLowerCase())));
        }
        if (auditCriteria.getUserId() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("userId"), auditCriteria.getUserId()));
        }
        if (auditCriteria.getUsername() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("username"), auditCriteria.getUsername()));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getSourceApplication())) {
            predicates.add(auditEntityRoot.get("auditSourceApplication").in(Arrays.asList(auditCriteria.getSourceApplication())));
        }
        if (auditCriteria.getSourceWorkstation() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("sourceWorkstation"), auditCriteria.getSourceWorkstation()));
        }
        if (auditCriteria.getHostname() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("hostname"), auditCriteria.getHostname()));
        }
        if (auditCriteria.getAuditTypeId() != null) {
            predicates.add(criteriaBuilder.equal(auditEntityRoot.get("auditTypeId"), auditCriteria.getAuditTypeId()));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getSender())) {
            predicates.add(auditEntityRoot.get("senderId").in(Arrays.asList(auditCriteria.getSender())));
        }
        if (ArrayUtils.isNotEmpty(auditCriteria.getReceiver())) {
            predicates.add(auditEntityRoot.get("receiverId").in(Arrays.asList(auditCriteria.getReceiver())));
        }

        return predicates.toArray(new Predicate[]{});
    }

    @Override
    public Stream<AuditEntity> streamSearch(AuditCriteria auditCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuditEntity> criteriaQuery = criteriaBuilder.createQuery(AuditEntity.class);
        Root<AuditEntity> auditEntityRoot = criteriaQuery.from(AuditEntity.class);

        criteriaQuery.select(auditEntityRoot).where(toPredicate(auditCriteria, criteriaBuilder, auditEntityRoot));

        TypedQuery<AuditEntity> query = entityManager.createQuery(criteriaQuery)
                .setHint(QueryHints.HINT_FETCH_SIZE, queryProperties.getHintFetchSize());

        if (queryProperties.getLimitSelectRow() > 0) {
            query.setMaxResults(queryProperties.getLimitSelectRow());
        }

        return query.getResultStream();
    }
}
