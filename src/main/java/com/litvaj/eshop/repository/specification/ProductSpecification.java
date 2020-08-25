package com.litvaj.eshop.repository.specification;

import com.litvaj.eshop.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private final String name;
    private final Double min;
    private final Double max;

    public ProductSpecification(String name, Double min, Double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(name)) {
            predicates.add(criteriaBuilder.like(root.get("name"), this.name + '%'));
        }
        if (min != null && min > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
        }
        if (max != null && max > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), max));
        }

        if (!predicates.isEmpty()) {
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }

        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}