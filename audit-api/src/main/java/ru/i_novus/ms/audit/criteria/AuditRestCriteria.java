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