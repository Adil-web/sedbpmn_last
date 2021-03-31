package kz.kazgisa.specification;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.enums.SearchOperation;
import kz.kazgisa.utils.CommonUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppSpecification implements Specification<App> {
    private List<SearchCriteriaDto> list;

    public AppSpecification() {
        this.list = new ArrayList<>();
    }

    public AppSpecification(List<SearchCriteriaDto> list) {
        this.list = list;
    }

    @Override
    public Predicate toPredicate(Root<App> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteriaDto criteria : list) {
            Field field = CommonUtils.getField(criteria.getKey(), App.class);
            Class<?> type = field.getType();
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Long.class)) {
                    predicates.add(builder.greaterThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (type.isAssignableFrom(Date.class)) {
                    predicates.add(builder.greaterThan(
                            root.get(criteria.getKey()), new Date((long) criteria.getValue())));
                }
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Long.class)) {
                    predicates.add(builder.lessThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (type.isAssignableFrom(Date.class)) {
                    predicates.add(builder.lessThan(
                            root.get(criteria.getKey()), new Date((long) criteria.getValue())));
                }

            }
            /*else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } */
            else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                if(criteria.getKey().equals("factEndDate") && criteria.getValue() == null) {
                    predicates.add(builder.isNotNull(
                            root.get(criteria.getKey())));
                } else {
                    predicates.add(builder.notEqual(
                            root.get(criteria.getKey()), criteria.getValue()));
                }
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                if(criteria.getKey().startsWith("subservice")) {
                    Join<App, Subservice> groupJoin = root.join("subservice");
                    predicates.add(builder.equal(groupJoin.get("id"),criteria.getValue().toString()));
                } else
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(builder.in(root.get(criteria.getKey())).value(
                        criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
