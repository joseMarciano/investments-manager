package com.investment.managment.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public final class SpecificationUtil {

    private SpecificationUtil() {
    }

    public static <T> Specification<T> like(final Set<String> props, final String term) {
        return (root, query, criteriaBuilder) -> {
            final List<Specification<T>> specifications = ofNullable(props)
                    .orElse(new HashSet<>())
                    .stream()
                    .map(SpecificationUtil.<T>createCriteriaLike(term))
                    .toList();

            return specifications.stream().reduce(Specification::or)
                    .map(toPredicate(root, query, criteriaBuilder)).orElse(null);
        };
    }

    public static <T> Specification<T> equal(final String prop, final String id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(prop), id);
    }

    private static <T> Function<Specification<T>, Predicate> toPredicate(final Root<T> root,
                                                                         final CriteriaQuery<?> query,
                                                                         final CriteriaBuilder criteriaBuilder) {
        return specification -> specification.toPredicate(root, query, criteriaBuilder);
    }

    private static <T> Function<String, Specification<T>> createCriteriaLike(final String term) {
        return prop -> (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(prop)),
                ("%" + term + "%").toUpperCase()
        );
    }

//    private static <T> Function<String, CriteriaBuilder> createCriteriaLike(final String term, final Root<T> root, final CriteriaQuery<T> criteriaQuery, final CriteriaBuilder criteriaBuilder) {
//        return prop -> {
//
//        }
//    }
}
