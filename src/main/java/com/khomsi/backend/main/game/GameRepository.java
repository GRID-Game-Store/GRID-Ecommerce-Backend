package com.khomsi.backend.main.game;

import com.khomsi.backend.main.game.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("SELECT g FROM Game g JOIN g.genres genre WHERE genre.name = :genre")
    List<Game> findGamesByGenre(@Param("genre") String genre);

    @Query("SELECT game FROM Game game ORDER BY game.releaseDate DESC")
    List<Game> findGamesByEarliestReleaseDate();

    @Query("SELECT game FROM Game game WHERE game.discount > 0")
    List<Game> findGamesWithDiscount();

    @Query(value = "SELECT g FROM Game g WHERE UPPER(g.title) LIKE CONCAT('%', UPPER(:text), '%')")
    List<Game> findSimilarTitles(@Param("text") String text);

    Optional<Game> findByTitleIgnoreCase(String title);

}
