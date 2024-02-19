package com.khomsi.backend.main.checkout.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transaction_games")
public class TransactionGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    private Game games;

    @NotNull
    @Column(name = "price_on_pay", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceOnPay;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transactions_id", nullable = false)
    @JsonIgnore
    private Transaction transactions;

}