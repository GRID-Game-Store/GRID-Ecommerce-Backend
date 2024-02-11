package com.khomsi.backend.additional.cart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "users_id", nullable = false)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserInfo user;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    private Game games;

    @Column(name = "created_date")
    private LocalDate createdDate;

    public Cart(Game games, UserInfo user) {
        this.user = user;
        this.games = games;
        this.createdDate = LocalDate.now();
    }
}