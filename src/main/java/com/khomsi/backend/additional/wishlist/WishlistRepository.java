package com.khomsi.backend.additional.wishlist;

import com.khomsi.backend.additional.wishlist.model.entity.Wishlist;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUsersOrderByAddedDate(UserInfo userInfo);

    Wishlist findByUsersAndGames(UserInfo userInfo, Game game);

    Boolean existsByUsersAndGames(UserInfo userInfo, Game game);
}
