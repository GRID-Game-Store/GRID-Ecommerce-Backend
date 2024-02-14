package com.khomsi.backend.additional.cart.mapper.impl;

import com.khomsi.backend.additional.cart.mapper.CartMapper;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.main.game.mapper.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartMapperImpl implements CartMapper {
    private final GameMapper gameMapper;

    @Override
    public CartItemDto toDtoFromCart(Cart cart) {
        return CartItemDto.builder()
                .cartId(cart.getId())
                .game(gameMapper.toShortGame(cart.getGames()))
                .userId(cart.getUser().getExternalId())
                .build();
    }
}