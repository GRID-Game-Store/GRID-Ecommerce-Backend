package com.khomsi.backend.main.checkout.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.checkout.model.enums.BalanceAction;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Size(max = 255)
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @JsonIgnore
    private UserInfo users;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10)
    private BigDecimal totalAmount;

    @Size(max = 150)
    @NotNull
    @Column(name = "payment_methods", nullable = false, length = 150)
    private String paymentMethods;
    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @OneToMany(mappedBy = "transactions", cascade = CascadeType.ALL)
    private Set<TransactionGames> transactionGames;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "balance_action", nullable = false, length = 60)
    private BalanceAction balanceAction;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Lob
    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "used_balance", precision = 10, scale = 2)
    private BigDecimal usedBalance;
}