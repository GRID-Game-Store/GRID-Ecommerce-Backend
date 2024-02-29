package com.khomsi.backend.main.game.service;

import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

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

    static Specification<Game> byTagIds(List<Integer> tagIds) {
        return (root, query, criteriaBuilder) -> {
            if (tagIds != null && !tagIds.isEmpty()) {
                // Subquery to calculate the number of unique tags for each game
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<Game> subRoot = subquery.from(Game.class);
                Join<Game, Tag> tagJoin = subRoot.join("tags");
                subquery.select(subRoot.get("id"))
                        .where(tagJoin.get("id").in(tagIds))
                        .groupBy(subRoot.get("id"))
                        .having(criteriaBuilder.equal(criteriaBuilder.countDistinct(tagJoin), tagIds.size()));

                return criteriaBuilder.in(root.get("id")).value(subquery);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    static Specification<Game> byField(String tableName, String fieldName, String fieldValue) {
        return (root, query, criteriaBuilder) ->
                fieldValue != null ?
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(tableName).get(fieldName)),
                                "%" + fieldValue.toLowerCase() + "%") :
                        criteriaBuilder.conjunction();
    }
}
