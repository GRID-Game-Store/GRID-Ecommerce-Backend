package com.khomsi.backend.additional.cart;

import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserOrderByCreatedDate(UserInfo userInfo);
    List<Cart> findAllByUserExternalId(String userId);
    Cart findByUserAndGames(UserInfo user, Game game);
    void deleteAllByUserExternalId(String userId);
}