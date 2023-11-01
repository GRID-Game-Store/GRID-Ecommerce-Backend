package com.khomsi.grid.main.game;

import com.khomsi.grid.main.game.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findGameByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}
