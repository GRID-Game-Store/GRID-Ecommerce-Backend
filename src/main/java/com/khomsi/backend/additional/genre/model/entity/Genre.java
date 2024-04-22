package com.khomsi.backend.additional.genre.model.entity;

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
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JsonIgnore
    private Set<Game> games;

    @PreRemove
    public void removeGenre() {
        games.forEach(game -> game.getGenres().removeIf(e -> e.equals(this)));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Genre genre)) return false;
        return getId() != null && getId().equals(genre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Genre.class);
    }
}