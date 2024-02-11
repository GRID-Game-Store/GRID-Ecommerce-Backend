package com.khomsi.backend.additional.cart.service;

import com.khomsi.backend.additional.cart.CartRepository;
import com.khomsi.backend.additional.cart.mapper.CartMapper;
import com.khomsi.backend.additional.cart.model.dto.AddToCartDto;
import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.additional.cart.model.response.CartResponse;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserInfoService userInfoService;
    private final GameService gameService;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addToCart(AddToCartDto addToCartDto) {
        UserInfo existingUser = userInfoService.getUserInfo();
        Game game = gameService.getGameById(addToCartDto.gameId());
        cartRepository.save(new Cart(game, addToCartDto.quantity(), existingUser));
        return new CartResponse("Successfully added to cart!");
    }

    @Override
    public CartDTO cartItems() {
        UserInfo existingUser = userInfoService.getUserInfo();
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDate(existingUser);
        List<CartItemDto> cartItems = cartList.stream().map(cartMapper::toDtoFromCart).toList();
        BigDecimal totalCost = cartItems.stream()
                .map(cartItemDto -> cartItemDto.game().price().multiply(
                        BigDecimal.valueOf(cartItemDto.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartDTO(cartItems, totalCost);
    }

    @Override
    public CartResponse updateCartItem(Long cartItemId, AddToCartDto cartDto) {
        Cart cart = cartRepository.getReferenceById(cartItemId);
        userInfoService.checkPermissionToAction(cart.getUser().getExternalId());
        cart.setQuantity(cartDto.quantity());
        cart.setCreatedDate(LocalDate.now());
        cartRepository.save(cart);
        return new CartResponse("Cart has been successfully updated!");
    }

    @Override
    public CartResponse deleteCartItem(Long itemID) {
        Cart cart = cartRepository.findById(itemID).orElseThrow(() ->
                new GlobalServiceException(HttpStatus.NOT_FOUND, "Cart id is invalid : " + itemID));
        userInfoService.checkPermissionToAction(cart.getUser().getExternalId());
        cartRepository.deleteById(itemID);
        return new CartResponse("Cart item with id " + itemID + " was successfully deleted!");
    }

    @Override
    public CartResponse cleanCartItems() {
        FullUserInfoDTO currentUser = userInfoService.getCurrentUser();
        String externalId = currentUser.externalId();
        List<Cart> cartList = cartRepository.findAllByUserExternalId(externalId);
        if (cartList == null || cartList.isEmpty()) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Action is not required for empty cart.");
        }
        cartRepository.deleteAllByUserExternalId(externalId);
        return new CartResponse("Cart items was successfully deleted for user %s !".formatted(externalId));
    }
}