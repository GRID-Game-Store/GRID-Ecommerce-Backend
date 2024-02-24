package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.additional.cart.model.dto.CartDTO;
import com.khomsi.backend.additional.cart.model.dto.CartItemDto;
import com.khomsi.backend.additional.cart.service.CartService;
import com.khomsi.backend.main.checkout.mapper.TransactionMapper;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.repository.TransactionGamesRepository;
import com.khomsi.backend.main.checkout.repository.TransactionRepository;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.game.service.GameService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.UserInfoRepository;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final GameService gameService;
    private final CartService cartService;
    private final UserInfoService userInfoService;
    private final TransactionRepository transactionRepository;
    private final TransactionGamesRepository transactionGamesRepository;
    private final TransactionMapper transactionMapper;
    private final UserInfoRepository userInfoRepository;

    @Override
    public void completeTransaction(String sessionId) {
        Transaction transaction = transactionRepository.findById(sessionId)
                .orElseThrow(() -> new GlobalServiceException(HttpStatus.BAD_REQUEST,
                        "Transaction " + sessionId + " is not found."));
        if (Boolean.TRUE.equals(transaction.getWithBalance())) {
            UserInfo user = transaction.getUsers();
            BigDecimal newBalance = user.getBalance().subtract(transaction.getUsedBalance());
            user.setBalance(newBalance);
            userInfoRepository.save(user);
        }
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setPaid(true);
        transaction.setRedirectUrl(null);
        transactionRepository.save(transaction);
    }

    @Override
    public void placeTemporaryTransaction(String sessionId, String url, boolean withBalance,
                                          PaymentMethod paymentMethod) {
        UserInfo existingUser = userInfoService.getUserInfo();
        Transaction transaction = new Transaction();
        CartDTO cartDto = cartService.cartItems();
        List<CartItemDto> cartItemDtoList = cartDto.cartItems();
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionId(sessionId);
        transaction.setUsers(existingUser);
        transaction.setTotalAmount(cartDto.totalCost());
        transaction.setPaymentMethods(String.valueOf(paymentMethod));
        transaction.setPaid(false);
        transaction.setRedirectUrl(url);
        transaction.setWithBalance(withBalance);
        // Calculate balance used if payment is made with balance
        if (withBalance) {
            BigDecimal totalAmount = cartDto.totalCost();
            BigDecimal balance = userInfoService.getUserInfo().getBalance();
            BigDecimal balanceUsed = balance.min(totalAmount);
            transaction.setUsedBalance(balanceUsed);
        }
        transactionRepository.save(transaction);
        //Set games to user's transaction
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

    @Override
    public BigDecimal getTotalAmountForBill(boolean withBalance, List<CartItemDto> cartItemDtoList) {
        BigDecimal totalAmount = cartItemDtoList.stream()
                .map(cartItem -> cartItem.game().price())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // If withBalance is true and balance is sufficient, deduct balance from totalAmount
        if (withBalance) {
            BigDecimal balance = userInfoService.getUserInfo().getBalance();
            if (balance.compareTo(totalAmount) >= 0) {
                throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Balance is higher than the total amount. " +
                        "Please choose a payment with balance instead.");
            } else {
                totalAmount = totalAmount.subtract(balance);
            }
        }
        return totalAmount;
    }

    @Override
    public List<TransactionDTO> transactionList() {
        UserInfo existingUser = userInfoService.getUserInfo();
        List<Transaction> transactions = transactionRepository.findAllByUsersOrderByCreatedAtDesc(existingUser);
        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .toList();
    }
}
