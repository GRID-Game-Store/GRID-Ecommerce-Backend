package com.khomsi.backend.additional.media.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game_medias")
public class GameMedia {
    @Id
    @Column(name = "games_id", nullable = false)
    @JsonIgnore
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "games_id", nullable = false)
    @JsonIgnore
    private Game games;

    @Lob
    @Column(name = "banner_url")
    private String bannerUrl;

    @Lob
    @Column(name = "screenshot_url")
    private String screenshotUrl;

    @Lob
    @Column(name = "trailer")
    private String trailer;

    @Lob
    @Column(name = "trailer_screenshot")
    private String trailerScreenshot;

}