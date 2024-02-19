package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.service.CartService;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.repository.TransactionGamesRepository;
import com.khomsi.backend.main.checkout.repository.TransactionRepository;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final GameService gameService;
    private final CartService cartService;
    private final UserInfoService userInfoService;
    private final TransactionRepository transactionRepository;
    private final TransactionGamesRepository transactionGamesRepository;

    @Override
    public void placeOrder(String sessionId, PaymentMethod paymentMethod) {
        UserInfo existingUser = userInfoService.getUserInfo();
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();

        // create the transaction
        Transaction transaction = new Transaction();
        transaction.setCreatedAt(new Date());
        transaction.setTransactionId(sessionId);
        transaction.setUsers(existingUser);
        transaction.setTotalAmount(cartDto.totalCost());
        transaction.setPaymentMethods(String.valueOf(paymentMethod));
        transaction.setPaid(true);
        transactionRepository.save(transaction);

        cartItemDtoList.forEach(cartItemDto -> {
            Game game = gameService.getGameById(cartItemDto.game().id());
            TransactionGames orderItem = new TransactionGames();
            orderItem.setPriceOnPay(cartItemDto.game().price());
            orderItem.setGames(game);
            orderItem.setTransactions(transaction);
            transactionGamesRepository.save(orderItem);
        });
        // cleanup user's cart
        cartService.cleanCartItems();
    }
}
