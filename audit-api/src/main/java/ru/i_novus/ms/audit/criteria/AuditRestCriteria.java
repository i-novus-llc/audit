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

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akuznetcov
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuditRestCriteria extends RestCriteria {

    @JsonIgnore
    protected List<Sort.Order> defaultOrders = new ArrayList<>();

    @Override
    public List<Sort.Order> getDefaultOrders() {
        return defaultOrders;
    }
}