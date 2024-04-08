package com.khomsi.backend.additional.wishlist;

import com.khomsi.backend.additional.wishlist.model.entity.Wishlist;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT w FROM Wishlist w JOIN FETCH w.users JOIN FETCH w.games WHERE w.games.discount > 0")
    List<Wishlist> findAllWithDiscountedGames();
    List<Wishlist> findAllByUsersOrderByAddedDate(UserInfo userInfo);

    Wishlist findByUsersAndGames(UserInfo userInfo, Game game);

    Boolean existsByUsersAndGames(UserInfo userInfo, Game game);
}
