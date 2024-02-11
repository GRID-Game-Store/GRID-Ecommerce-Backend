package com.khomsi.backend.additional.cart;

import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserOrderByCreatedDate(UserInfo userInfo);
    List<Cart> findAllByUserExternalId(String userId);
    void deleteAllByUserExternalId(String userId);
}