package com.khomsi.backend.additional.cart.service;

import com.khomsi.backend.additional.cart.CartRepository;
import com.khomsi.backend.additional.cart.mapper.CartMapper;
import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.additional.cart.model.response.CartResponse;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserGamesService;
import com.khomsi.backend.main.user.service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
//TODO test this service all
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserInfoService userInfoService;
    private final GameService gameService;
    private final CartMapper cartMapper;
    private final UserGamesService userGamesService;

    @Override
    public CartResponse addToCart(Long gameId) {
        //TODO check the method since it saves the game that is in library and transactions
        UserInfo existingUser = userInfoService.getUserInfo();
        Game game = gameService.getActiveGameById(gameId);
        boolean gameAlreadyInLibrary = userGamesService.checkIfGameExists(existingUser, game);
        //Check if the game is already is library
        if (gameAlreadyInLibrary) {
            return new CartResponse("Game is already in the library.");
        }
        // Check if game is free
        if (game.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            userGamesService.saveUserGames(existingUser, game);
            return new CartResponse("Game is free. Added directly to library.");
        }

        Cart existingCartEntry = cartRepository.findByUserAndGames(existingUser, game);
        if (existingCartEntry != null) {
            return new CartResponse("Game is already in the cart.");
        }
        cartRepository.save(new Cart(game, existingUser));
        return new CartResponse("Successfully added to cart!");
    }


    @Override
    public CartDTO cartItems() {
        UserInfo existingUser = userInfoService.getUserInfo();
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDate(existingUser);
        List<CartItemDto> cartItems = cartList.stream().map(cartMapper::toDtoFromCart).toList();
        BigDecimal totalCost = getTotalCost(cartItems);
        return new CartDTO(cartItems, totalCost);
    }

    private BigDecimal getTotalCost(List<CartItemDto> cartItems) {
        return cartItems.stream()
                .map(cartItemDto -> cartItemDto.game().price())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    //TODO doesn't delete
    public CartResponse deleteCartItem(Long itemID) {
        Cart cart = cartRepository.findById(itemID).orElseThrow(() ->
                new GlobalServiceException(HttpStatus.NOT_FOUND, "Cart id is invalid : " + itemID));
//        userInfoService.checkPermissionToAction(cart.getUser().getExternalId());
        cartRepository.delete(cart);
        return new CartResponse("Cart item with id " + itemID + " was successfully deleted!");
    }

    @Override
    //TODO doesn't delete
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