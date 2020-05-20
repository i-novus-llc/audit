package ru.i_novus.ms.audit.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akuznetcov
 **/
@Getter
@Setter
public class AuditRestCriteria extends RestCriteria {

    @JsonIgnore
    protected List<Sort.Order> defaultOrders = new ArrayList<>();

    @JsonIgnore
    public List<Sort.Order> getOrdersOrDefault() {
        return CollectionUtils.isEmpty(super.getOrders()) ? defaultOrders : super.getOrders();
    }
}