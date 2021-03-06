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

package ru.i_novus.ms.audit.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class AuditClientRequestParam {

    private String value;
    private Object[] args;

    @Override
    public String toString() {
        return "AuditClientRequestParam{" +
                "value='" + value + "'" +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof AuditClientRequestParam))
            return false;

        AuditClientRequestParam that = (AuditClientRequestParam) o;

        return Objects.equals(getValue(), that.getValue()) &&
                Arrays.equals(getArgs(), that.getArgs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getValue());
        result = 31 * result + Arrays.hashCode(getArgs());
        return result;
    }
}
