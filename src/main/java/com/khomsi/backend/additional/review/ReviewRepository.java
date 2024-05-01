package com.khomsi.backend.additional.review;

import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.games.id = :gameId order by r.reviewDate DESC")
    List<Review> findAllByGameIdOrderByReviewDate(Long gameId);

    Review findByUsersAndGames(UserInfo user, Game game);

    Page<Review> findAllByGamesId(Long gameId, Pageable pageable);
}
