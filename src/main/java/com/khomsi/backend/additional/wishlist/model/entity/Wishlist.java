package com.khomsi.backend.additional.wishlist.model.entity;

import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @Column(name = "wishlist_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private UserInfo users;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    private Game games;

    @Column(name = "added_date")
    private LocalDate addedDate;

    public Wishlist(UserInfo users, Game games) {
        this.users = users;
        this.games = games;
        this.addedDate = LocalDate.now();
    }

    public Wishlist() {

    }
}