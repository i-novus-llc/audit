package ru.i_novus.ms.audit.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

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
}
