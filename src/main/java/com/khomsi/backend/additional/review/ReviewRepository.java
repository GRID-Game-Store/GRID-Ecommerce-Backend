package com.khomsi.backend.additional.review;

import com.khomsi.backend.additional.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.games.id = :gameId order by r.reviewDate DESC")
    List<Review> findAllByGameIdOrderByReviewDate(Long gameId);
}
