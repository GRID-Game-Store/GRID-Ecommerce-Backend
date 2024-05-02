package com.khomsi.backend.main.game;

import com.khomsi.backend.main.game.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    @Query("SELECT g FROM Game g JOIN g.genres genre WHERE genre.name = :genre AND g.active = true")
    List<Game> findGamesByGenre(@Param("genre") String genre);

    @Query("SELECT game FROM Game game WHERE game.active = true ORDER BY game.releaseDate DESC")
    List<Game> findGamesByEarliestReleaseDate();

    @Query("SELECT game FROM Game game WHERE game.discount > 0 AND game.active = true")
    List<Game> findGamesWithDiscount();

    @Query(value = "SELECT g FROM Game g WHERE UPPER(g.title) LIKE CONCAT('%', UPPER(:text), '%') AND g.active = true")
    List<Game> findSimilarTitles(@Param("text") String text);

    @Query(value = "SELECT g FROM Game g WHERE UPPER(g.title) LIKE CONCAT('%', UPPER(:text), '%')")
    List<Game> findSimilarTitlesWithoutActiveCheck(@Param("text") String text);


    boolean existsGameByTitleIgnoreCase(String title);

    Optional<Game> findByIdAndActiveTrue(Long id);

    @Query("select g from Game g WHERE g.active = true")
    List<Game> findAllActiveGames();
}
