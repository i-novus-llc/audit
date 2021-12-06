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

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.i_novus.ms.audit.criteria.AuditObjectCriteria;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;
import ru.i_novus.ms.audit.model.AuditObject;
import ru.i_novus.ms.audit.repository.AuditObjectRepository;
import ru.i_novus.ms.audit.repository.AuditRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditObjectServiceTest {

    private static final String TEXT = "test";

    @Mock
    private AuditObjectRepository repository;
    @Mock
    private AuditRepository auditRepository;
    @InjectMocks
    private AuditObjectService service;

    @Test
    public void testCreateIfNotPresent() {
        doReturn(Optional.of(AuditObjectEntity.class)).when(repository).findByNameAndType(anyString(), anyString());
        service.createIfNotPresent(TEXT, TEXT);
        verify(repository, never()).save(any());
    }

    @Test
    public void testCreateIfNotPresentEmpty() {
        doReturn(Optional.empty()).when(repository).findByNameAndType(anyString(), anyString());
        service.createIfNotPresent(TEXT, TEXT);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void testSearchEmpty() {
        AuditObjectCriteria criteria = beforeSearch();
        doReturn(Page.empty()).when(repository)
                .findAll(any(Predicate.class), any(Pageable.class));
        doReturn(Collections.emptyList()).when(auditRepository).findAll(any(Predicate.class));
        Page<AuditObject> page = service.search(criteria);

        assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testSearch() {
        AuditObjectCriteria criteria = beforeSearch();
        List<AuditObjectEntity> entityList = Arrays.asList(
                new AuditObjectEntity(),
                new AuditObjectEntity(),
                new AuditObjectEntity()
        );

        doReturn(new PageImpl<>(entityList)).when(repository)
                .findAll(ArgumentCaptor.forClass(Predicate.class).capture(), ArgumentCaptor.forClass(Pageable.class).capture());
        doReturn(Collections.emptyList()).when(auditRepository).findAll(any(Predicate.class));
        Page<AuditObject> page = service.search(criteria);

        assertEquals(3, page.getTotalElements());
    }

    private AuditObjectCriteria beforeSearch() {
        AuditObjectCriteria criteria = new AuditObjectCriteria();
        criteria.setPageSize(10);
        return criteria;
    }
}
