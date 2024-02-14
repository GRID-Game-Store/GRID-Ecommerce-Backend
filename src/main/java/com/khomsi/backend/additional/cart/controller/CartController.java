package com.khomsi.backend.additional.cart.controller;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.response.CartResponse;
import com.khomsi.backend.additional.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/api/v1/cart")
@Validated
@RequiredArgsConstructor
@Tag(name = "Cart", description = "CRUD operation for Cart Controller")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get all items in user's cart")
    public ResponseEntity<CartDTO> getCartItems() {
        return new ResponseEntity<>(cartService.cartItems(), HttpStatus.OK);
    }

    @PostMapping("/add/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Add item to user's cart")
    public ResponseEntity<CartResponse> addToCart(@PathVariable("game-id") @Min(1) @Max(Long.MAX_VALUE) Long gameId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(gameId));
    }
    @DeleteMapping("/delete/{cart-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Delete item from user's cart")
    public ResponseEntity<CartResponse> deleteCartItem(@PathVariable("cart-id")
                                                       Long cartItemId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCartItem(cartItemId));
    }

    @DeleteMapping("/cleanup")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Cleanup whole user's cart")
    public ResponseEntity<CartResponse> deleteAllCartItems() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.cleanCartItems());
    }
}
