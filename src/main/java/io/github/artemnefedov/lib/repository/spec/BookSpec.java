package io.github.artemnefedov.lib.repository.spec;

import io.github.artemnefedov.lib.repository.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class BookSpec implements Specification<Book> {

    private final List<SearchCriteria> params;

    public BookSpec(List<SearchCriteria> params) {
        this.params = params;
    }

    public void add(SearchCriteria criteria) {
        params.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query,
            CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : params) {
            String op = criteria.operation();
            Path<Object> path = root.get(criteria.key());

            switch (op) {
                case "=" -> predicates.add(builder.equal(path, criteria.value()));
                case "like" -> predicates.add(
                        builder.like(builder.lower(path.as(String.class)), "%" + criteria.value().toString().toLowerCase() + "%")
                );
                case ">" -> predicates.add(builder.greaterThan(path.as(String.class), criteria.value().toString()));
                case "<" -> predicates.add(builder.lessThan(path.as(String.class), criteria.value().toString()));
                case ">=" -> predicates.add(builder.greaterThanOrEqualTo(path.as(String.class), criteria.value().toString()));
                case "<=" -> predicates.add(builder.lessThanOrEqualTo(path.as(String.class), criteria.value().toString()));
                default -> throw new IllegalArgumentException("Unsupported operation: " + op);
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
