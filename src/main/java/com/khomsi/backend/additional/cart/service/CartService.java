package com.khomsi.backend.additional.cart.service;

import com.khomsi.backend.additional.cart.model.dto.AddToCartDto;
import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.response.CartResponse;

public interface CartService {
    CartResponse addToCart(AddToCartDto addToCartDto);

    CartDTO cartItems();

    CartResponse updateCartItem(Long cartItemId, AddToCartDto cartDto);

    CartResponse deleteCartItem(Long gameId);

    CartResponse cleanCartItems();
}
