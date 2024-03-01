package com.khomsi.backend.main.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "user_has_games")
public class UserGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @JsonIgnore
    private UserInfo user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    private Game game;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private Instant purchaseDate;

    @NotNull
    @Column(name = "playtime", nullable = false)
    private LocalTime playtime;

}