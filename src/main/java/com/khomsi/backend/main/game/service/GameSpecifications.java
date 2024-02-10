package com.khomsi.backend.main.game.service;

import com.khomsi.backend.main.game.model.entity.Game;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GameSpecifications {
    static Specification<Game> byTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title != null ?
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"
                                + title.toLowerCase() + "%") :
                        criteriaBuilder.conjunction();
    }
    static Specification<Game> byMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice != null ?
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice) :
                        criteriaBuilder.conjunction();
    }
    static Set<Long> parseIds(String ids) {
        try (Stream<String> stream = Arrays.stream(ids != null ? ids.split(",") : new String[0])) {
            return stream.map(Long::valueOf).collect(Collectors.toSet());
        } catch (NumberFormatException e) {
            return Collections.emptySet();
        }
    }

    static Specification<Game> byIds(Set<Long> ids, String fieldJoin) {
        return (root, query, criteriaBuilder) ->
                ids == null || ids.isEmpty() ? criteriaBuilder.conjunction()
                        : root.join(fieldJoin).get("id").in(ids);
    }
}
