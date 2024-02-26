package com.khomsi.backend.main.game.service;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.main.game.model.dto.GameCriteria;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.*;
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
    static Specification<Game> byTagsIds(Set<Long> tagIds) {
        return (root, query, criteriaBuilder) -> {
            // Подзапрос для вычисления количества уникальных тегов для каждой игры
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Game> subRoot = subquery.from(Game.class);
            Join<Game, Tag> tagJoin = subRoot.join("tags");
            subquery.select(subRoot.get("id"))
                    .where(tagJoin.get("id").in(tagIds))
                    .groupBy(subRoot.get("id"))
                    .having(criteriaBuilder.equal(criteriaBuilder.countDistinct(tagJoin), tagIds.size()));

            // Возвращаем предикат, который связывает корневой запрос с подзапросом
            return criteriaBuilder.in(root.get("id")).value(subquery);
        };
    }
    static Specification<Game> byCriteria(GameCriteria gameCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Фильтрация по тегам
            Set<Long> tagIds = GameSpecifications.parseIds(gameCriteria.getTags());
            if (!tagIds.isEmpty()) {
                Join<Game, Tag> tagJoin = root.join("tags");
                predicates.add(tagJoin.get("id").in(tagIds));
            }

            // Фильтрация по жанрам
            // Аналогично для других критериев поиска (жанры, платформы, разработчики, издатели)
            Set<Long> genreIds = GameSpecifications.parseIds(gameCriteria.getGenres());
            if (!genreIds.isEmpty()) {
                Join<Game, Genre> genreJoin = root.join("genres");
                predicates.add(genreJoin.get("id").in(genreIds));
            }

            // Комбинируем все предикаты в один
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
