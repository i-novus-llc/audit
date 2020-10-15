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

package ru.i_novus.ms.audit.client.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.n2oapp.platform.jaxrs.MapperConfigurer;
import org.springframework.stereotype.Component;
import ru.i_novus.ms.audit.model.Audit;
import ru.i_novus.ms.audit.model.AuditForm;

@Component
public class AuditMapperConfigurer implements MapperConfigurer {
    @Override
    public void configure(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Audit.class, AuditMixIn.class);
        objectMapper.addMixIn(AuditForm.class, AuditFormMixIn.class);
    }
}
