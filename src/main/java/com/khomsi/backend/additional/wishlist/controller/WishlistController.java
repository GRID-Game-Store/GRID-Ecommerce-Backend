package com.khomsi.backend.additional.wishlist.controller;

import com.khomsi.backend.additional.wishlist.model.response.WishListResponse;
import com.khomsi.backend.additional.wishlist.service.WishlistService;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Wishlist", description = "CRUD operation for Wishlist Controller")
@RequestMapping("/api/v1/wishlist")
@Validated
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Show wishlist of user")
    public List<ShortGameModel> getUserWishList() {
        return wishlistService.getWishListByUser();
    }

    @PostMapping("/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add game to wishlist")
    public ResponseEntity<WishListResponse> addGameToWishlist(@PathVariable("game-id")
                                                              @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return wishlistService.createWishlist(gameId);
    }
    @DeleteMapping("/delete/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete game to wishlist")
    public ResponseEntity<WishListResponse> deleteGameFromWishlist(@PathVariable("game-id")
                                                              @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return wishlistService.deleteGameFromWishlist(gameId);
    }
    @PostMapping("/check/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Check if game is already in wishlist and in library")
    public Boolean checkIfGameIsInWishlist(
            @PathVariable("game-id")
            @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return wishlistService.checkIfGamesIsInWishlist(gameId);
    }
}
