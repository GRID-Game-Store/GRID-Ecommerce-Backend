package com.khomsi.backend.main.checkout.model.entity;

import com.khomsi.backend.main.user.model.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
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
    private UserInfo users;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

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

    @OneToMany(mappedBy = "transactions")
    private Set<TransactionGames> transactionGames = new LinkedHashSet<>();


}