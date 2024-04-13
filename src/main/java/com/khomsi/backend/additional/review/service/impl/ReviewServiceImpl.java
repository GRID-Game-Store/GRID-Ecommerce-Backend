package com.khomsi.backend.additional.review.service.impl;

import com.khomsi.backend.additional.review.ReviewRepository;
import com.khomsi.backend.additional.review.model.dto.ReviewDTO;
import com.khomsi.backend.additional.review.model.dto.ReviewRequest;
import com.khomsi.backend.additional.review.model.dto.ReviewResponse;
import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.additional.review.service.ReviewService;
import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserInfoService userInfoService;
    private final GameService gameService;
    private final GameMapper gameMapper;

    @Override
    public ResponseEntity<ReviewResponse> addReview(Long gameId, ReviewRequest reviewRequest) {
        UserInfo existingUser = userInfoService.getUserInfo();
        Game game = gameService.getGameById(gameId);
        checkUserCanReview(existingUser, game);

        Review review = new Review(existingUser, game, reviewRequest.rating(), reviewRequest.comment());
        reviewRepository.save(review);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReviewResponse("Review with id " + review.getId() + " has been created!"));
    }

    @Override
    public ResponseEntity<ReviewResponse> editReview(Long reviewId, ReviewRequest reviewRequest) {
        UserInfo existingUser = userInfoService.getUserInfo();
        Review review = getReview(reviewId);
        checkUserCanEditReview(existingUser, review);

        review.setComment(reviewRequest.comment());
        review.setRating(reviewRequest.rating());
        review.setReviewDate(LocalDateTime.now());
        reviewRepository.save(review);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReviewResponse("Review with id " + review.getId() + " has been changed!"));
    }

    @Override
    public ResponseEntity<ReviewResponse> deleteReview(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.delete(review);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReviewResponse("Review with id " + reviewId + " was successfully deleted."));
    }

    @Override
    public ResponseEntity<ReviewDTO> getReviewForGameByUser(Long gameId) {
        UserInfo existingUser = userInfoService.getUserInfo();
        Game game = gameService.getGameById(gameId);
        Review review = reviewRepository.findByUsersAndGames(existingUser, game);
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapReviewToDTO(review));
    }

    @Override
    public List<ReviewDTO> viewReview(Long gameId) {
        List<Review> reviews = reviewRepository.findAllByGameIdOrderByReviewDate(gameId);
        return reviews.stream()
                .map(this::mapReviewToDTO)
                .toList();
    }

    private void checkUserCanReview(UserInfo user, Game game) {
        if (isGameNotInLibrary(game, user)) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "User cannot review this game.");
        }
        if (isReviewedGame(game, user)) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "User has already reviewed this game.");
        }
    }

    private void checkUserCanEditReview(UserInfo user, Review review) {
        if (!review.getUsers().equals(user)) {
            throw new GlobalServiceException(HttpStatus.FORBIDDEN, "User cannot edit this review.");
        }
        boolean doesNotHaveGameInLibrary = isGameNotInLibrary(review.getGames(), user);
        boolean hasReviewedGame = isReviewedGame(review.getGames(), user);
        // Handle the case where the user doesn't meet the conditions
        if (doesNotHaveGameInLibrary || !hasReviewedGame) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "User cannot review this game.");
        }
    }

    private boolean isReviewedGame(Game game, UserInfo existingUser) {
        // Check if the user has already written a review for the game
        return existingUser.getReviews()
                .stream().anyMatch(review -> review.getGames().equals(game));
    }

    private boolean isGameNotInLibrary(Game game, UserInfo existingUser) {
        // Check if the user has the game in their library
        return existingUser.getUserGames()
                .stream().noneMatch(userGames -> userGames.getGame().equals(game));
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND,
                        "Review with id " + reviewId + " is not found."));
    }

    private ReviewDTO mapReviewToDTO(Review review) {
        Game game = review.getGames();
        ShortGameModel shortGameModel = gameMapper.toShortGame(game, true);
        String username = review.getUsers().getUsername();
        return new ReviewDTO(
                review.getId(),
                username,
                shortGameModel,
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}
