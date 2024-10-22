package com.khomsi.backend.main.game.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.additional.cart.model.entity.Cart;
import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.media.model.entity.GameMedia;
import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.additional.review.model.entity.Review;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import com.khomsi.backend.additional.wishlist.model.entity.Wishlist;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.user.model.entity.UserGames;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "games")
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
    @Lob
    @Column(name = "system_requirements")
    private String systemRequirements;
    @Lob
    @Column(name = "about_game")
    private String aboutGame;

    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @Lob
    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @NotNull
    @Column(name = "discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @NotNull
    @Column(name = "permit_age", nullable = false)
    private String permitAge;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "developer_id", nullable = false)
    private Developer developer;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    @JoinTable(name = "games_has_tags",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Set<Tag> tags;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    @JoinTable(name = "games_has_genres",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "genres_id"))
    @ToString.Exclude
    private Set<Genre> genres;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,  CascadeType.REFRESH})
    @JoinTable(name = "games_has_platforms",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "platforms_id"))
    @ToString.Exclude
    private Set<Platform> platforms;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "games", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Cart> carts;
    @OneToOne(mappedBy = "games", cascade = CascadeType.ALL)
    private GameMedia gameMedia;

    @JsonIgnore
    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Review> review;

    @JsonIgnore
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<UserGames> userHasGames;

    @JsonIgnore
    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Wishlist> wishlists;
    @JsonIgnore
    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<TransactionGames> transactionGames;

    public BigDecimal getPrice() {
        if (discount == null || price == null) {
            return price;
        }
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100),
                RoundingMode.HALF_UP));
        return price.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
}