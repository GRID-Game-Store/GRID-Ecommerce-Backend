package com.khomsi.backend.additional.platform.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khomsi.backend.main.game.model.entity.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "platforms")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "platforms", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JsonIgnore
    private Set<Game> games;

    @PreRemove
    public void removePlatforms() {
        games.forEach(game -> game.getPlatforms().removeIf(e -> e.equals(this)));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Platform platform)) return false;
        return getId() != null && getId().equals(platform.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Platform.class);
    }
}