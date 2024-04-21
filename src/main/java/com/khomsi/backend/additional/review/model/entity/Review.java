package com.khomsi.backend.additional.review.model.entity;

import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private UserInfo users;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    private Game games;

    @NotNull
    @Column(name = "rating", nullable = false)
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotNull
    @Lob
    @Column(name = "comment", nullable = false)
    @Size(max = 2000, message = "Comment length must be less than or equal to 2000 characters")
    private String comment;

    @NotNull
    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;

    public Review(UserInfo users, Game games, Integer rating, String comment) {
        this.users = users;
        this.games = games;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDateTime.now();
    }

    public Review() {

    }
}