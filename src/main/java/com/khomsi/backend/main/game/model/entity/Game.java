package com.khomsi.backend.main.game.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khomsi.backend.additional.developer.model.entity.Developer;
import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.media.model.entity.GameMedia;
import com.khomsi.backend.additional.platform.model.entity.Platform;
import com.khomsi.backend.additional.publisher.model.entity.Publisher;
import com.khomsi.backend.additional.tag.model.entity.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Lob
    @Column(name = "system_requirements")
    private String systemRequirements;

    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @Lob
    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @NotNull
    @Column(name = "discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @NotNull
    @Lob
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

    @ManyToMany
    @JoinTable(name = "games_has_tags",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(name = "games_has_genres",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "genres_id"))
    @ToString.Exclude
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "games_has_platforms",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "platforms_id"))
    @ToString.Exclude
    private Set<Platform> platforms;

    @OneToOne(mappedBy = "games", cascade = CascadeType.ALL)
    private GameMedia gameMedia;

    public BigDecimal getPrice() {
        if (discount == null || price == null) {
            return price;
        }
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100),
                RoundingMode.HALF_UP));
        return price.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
}