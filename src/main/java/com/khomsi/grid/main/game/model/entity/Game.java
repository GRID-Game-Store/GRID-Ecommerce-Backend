package com.khomsi.grid.main.game.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.khomsi.grid.additional.developer.model.entity.Developer;
import com.khomsi.grid.additional.publisher.model.entity.Publisher;
import com.khomsi.grid.additional.tag.model.entity.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    @JsonBackReference
    private Tag tag;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonBackReference
    private Publisher publisher;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "developer_id", nullable = false)
    @JsonBackReference
    private Developer developer;

    /*    @ManyToMany
        @JoinTable(name = "games_has_genres",
                joinColumns = @JoinColumn(name = "game_id")
                , inverseJoinColumns = @JoinColumn(name = "genre_id"))
                @JsonBackReference
        private Set<Genre> genres;*/
}