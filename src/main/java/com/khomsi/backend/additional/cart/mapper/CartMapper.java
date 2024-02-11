package com.khomsi.backend.additional.cart.mapper;

import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.model.entity.Cart;

public interface CartMapper {
    CartItemDto toDtoFromCart(Cart cart);
}
