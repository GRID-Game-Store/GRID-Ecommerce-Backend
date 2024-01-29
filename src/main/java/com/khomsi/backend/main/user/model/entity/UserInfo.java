package com.khomsi.backend.main.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "users", uniqueConstraints =
@UniqueConstraint(columnNames = "email"))
public class UserInfo {
    @Id
    @Column(name = "id", nullable = false)
    private String externalId;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;
}