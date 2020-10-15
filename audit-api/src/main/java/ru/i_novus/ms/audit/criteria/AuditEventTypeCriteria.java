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

package ru.i_novus.ms.audit.criteria;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.QueryParam;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditEventTypeCriteria extends AuditRestCriteria {
    @ApiParam("Наименование")
    @QueryParam("name")
    private String name;

    @ApiParam("Идентификатор типа журнала")
    @QueryParam("auditTypeId")
    private Short auditTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditEventTypeCriteria)) return false;
        if (!super.equals(o)) return false;
        AuditEventTypeCriteria that = (AuditEventTypeCriteria) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(auditTypeId, that.auditTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, auditTypeId);
    }
}
