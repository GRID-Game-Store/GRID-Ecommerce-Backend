package com.khomsi.backend.additional.wishlist.service;

import com.khomsi.backend.additional.wishlist.model.response.WishListResponse;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WishlistService {
    List<ShortGameModel> getWishListByUser();

    ResponseEntity<WishListResponse> createWishlist(Long gameId);

    ResponseEntity<WishListResponse> deleteGameFromWishlist(Long gameId);

    boolean checkIfGamesIsInWishlist(Long gameId);
}
