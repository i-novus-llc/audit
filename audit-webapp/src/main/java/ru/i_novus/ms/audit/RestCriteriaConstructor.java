package ru.i_novus.ms.audit;

import net.n2oapp.framework.api.criteria.N2oPreparedCriteria;
import net.n2oapp.framework.api.data.CriteriaConstructor;
import net.n2oapp.platform.jaxrs.RestCriteria;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RestCriteriaConstructor implements CriteriaConstructor, Serializable {
    @Override
    public <T> T construct(N2oPreparedCriteria criteria, Class<T> criteriaClass) {
        T instance;
        try {
            instance =  criteriaClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
        if (instance instanceof RestCriteria) {
            ((RestCriteria) instance).setPageNumber(criteria.getPage() - 1);
            ((RestCriteria) instance).setPageSize(criteria.getSize());
            List<Sort.Order> orders = new ArrayList<>();
            if (criteria.getSorting() != null) {
                orders.add(new Sort.Order(Sort.Direction.fromString(criteria.getSorting()
                        .getDirection().getExpression()), criteria.getSorting().getField()));
            }
            ((RestCriteria) instance).setOrders(orders);
        }
        return instance;
    }
}
