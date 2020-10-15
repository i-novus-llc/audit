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

package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditSourceApplicationEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuditSourceApplicationRepository extends JpaRepository<AuditSourceApplicationEntity, UUID>, QuerydslPredicateExecutor<AuditSourceApplicationEntity> {

    @Query("select id from AuditSourceApplicationEntity ")
    String[] findAllSourceApplicationId();

    Optional<AuditSourceApplicationEntity> findByCode(String code);
}
