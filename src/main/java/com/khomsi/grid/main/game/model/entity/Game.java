package com.khomsi.grid.main.game.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khomsi.grid.additional.developer.model.entity.Developer;
import com.khomsi.grid.additional.genre.model.entity.Genre;
import com.khomsi.grid.additional.media.model.entity.GameMedia;
import com.khomsi.grid.additional.platform.model.entity.Platform;
import com.khomsi.grid.additional.publisher.model.entity.Publisher;
import com.khomsi.grid.additional.tag.model.entity.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "games")
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
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(name = "games_has_genres",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "genres_id"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "games_has_platforms",
            joinColumns = @JoinColumn(name = "games_id")
            , inverseJoinColumns = @JoinColumn(name = "platforms_id"))
    private Set<Platform> platforms;

    @OneToOne(mappedBy = "games", cascade = CascadeType.ALL)
    private GameMedia gameMedia;
}