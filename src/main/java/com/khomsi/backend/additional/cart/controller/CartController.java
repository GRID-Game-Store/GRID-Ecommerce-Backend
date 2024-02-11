package com.khomsi.backend.additional.cart.controller;

import com.khomsi.backend.additional.cart.model.dto.AddToCartDto;
import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.response.CartResponse;
import com.khomsi.backend.additional.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@Validated
@RequiredArgsConstructor
@Tag(name = "Cart", description = "CRUD operation for Cart Controller")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get all items in user's cart")
    public ResponseEntity<CartDTO> getCartItems() {
        return new ResponseEntity<>(cartService.cartItems(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(summary = "Add item to user's cart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody @Valid AddToCartDto addToCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(addToCartDto));
    }

    @PutMapping("/update/{cart-id}")
    @Operation(summary = "Update user's cart")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable("cart-id") @Min(1) @Max(Long.MAX_VALUE) Long cartItemId,
            @RequestBody @Valid AddToCartDto addToCartDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartItem(cartItemId, addToCartDto));
    }

    @DeleteMapping("/delete/{cart-id}")
    @Operation(summary = "Delete item from user's cart")
    public ResponseEntity<CartResponse> deleteCartItem(@PathVariable("cart-id")
                                                       Long cartItemId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.deleteCartItem(cartItemId));
    }

    @DeleteMapping("/cleanup")
    @Operation(summary = "Cleanup whole user's cart")
    public ResponseEntity<CartResponse> deleteAllCartItems() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.cleanCartItems());
    }
}
