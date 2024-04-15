package com.khomsi.backend.additional.wishlist.service;

import com.khomsi.backend.additional.wishlist.WishlistRepository;
import com.khomsi.backend.additional.wishlist.model.entity.Wishlist;
import com.khomsi.backend.additional.wishlist.model.response.WishListResponse;
import com.khomsi.backend.main.checkout.service.EmailService;
import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserInfoService userInfoService;
    private final GameService gameService;
    private final GameMapper gameMapper;
    private final EmailService emailService;
    //1 time in a week send the email
    public static final long FIXED_RATE_MILLISECONDS = 7 * 24 * 60 * 60 * 1000;

    @Override
    public List<ShortGameModel> getWishListByUser() {
        UserInfo user = userInfoService.getUserInfo();
        List<Wishlist> wishlist = wishlistRepository.findAllByUsersOrderByAddedDate(user);
        return wishlist.stream()
                .map(w -> gameMapper.toShortGame(w.getGames()))
                .toList();
    }

    @Scheduled(fixedRate = FIXED_RATE_MILLISECONDS)
    public void sendDiscountNotificationEmails() {
        // Collect the discounted games using Java streams
        Map<UserInfo, List<ShortGameModel>> discountedGamesByUser = wishlistRepository.findAllWithDiscountedGames().stream()
                .collect(Collectors.groupingBy(Wishlist::getUsers, // Group by user
                        Collectors.mapping(wishlist -> gameMapper.toShortGame(wishlist.getGames()), // Map Game objects to ShortGame
                                Collectors.toList()))); // Collect ShortGame objects into a list
        // Iterate over each user and their discounted games
        discountedGamesByUser.forEach((user, discountedGamesForUser) ->
                emailService.sendDiscountNotificationEmail(discountedGamesForUser, user)
        );
    }

    @Override
    public ResponseEntity<WishListResponse> createWishlist(Long gameId) {
        Game game = gameService.getActiveGameById(gameId);
        if (checkIfGamesIsInWishlist(game.getId())) {
            return new ResponseEntity<>(new WishListResponse("Game already exists in wishlist!"), HttpStatus.BAD_REQUEST);
        }
        UserInfo user = userInfoService.getUserInfo();
        Wishlist wishlist = new Wishlist(user, game);
        wishlistRepository.save(wishlist);
        return new ResponseEntity<>(new WishListResponse("Successfully added to wishlist!"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<WishListResponse> deleteGameFromWishlist(Long gameId) {
        UserInfo user = userInfoService.getUserInfo();
        Game game = gameService.getActiveGameById(gameId);
        Wishlist wishlist = wishlistRepository.findByUsersAndGames(user, game);
        if (wishlist == null) {
            return new ResponseEntity<>(new WishListResponse("Game is not found in wishlist!"), HttpStatus.NOT_FOUND);
        }
        wishlistRepository.delete(wishlist);
        return new ResponseEntity<>(new WishListResponse("Game successfully removed from wishlist!"), HttpStatus.OK);
    }

    @Override
    public boolean checkIfGamesIsInWishlist(Long gameId) {
        UserInfo user = userInfoService.getUserInfo();
        Game game = gameService.getActiveGameById(gameId);
        // Check if the game exists in the user's wishlist
        boolean isInWishlist = wishlistRepository.existsByUsersAndGames(user, game);
        // Check if the game exists in the user's library
        boolean isInLibrary = userInfoService.checkIfGameIsOwnedByCurrentUser(game);
        // Return false if the game is either in the wishlist or library, otherwise return true
        return isInWishlist || isInLibrary;
    }
}
