package com.khomsi.backend.additional.cart.service;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.response.CartResponse;

public interface CartService {
    CartResponse addToCart(Long gameId);

    CartDTO cartItems();

    CartResponse deleteCartItem(Long gameId);

    CartResponse cleanCartItems();
}
