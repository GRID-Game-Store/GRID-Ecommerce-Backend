package com.khomsi.backend.additional.tag.model.entity;

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
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Game> games;

    @PreRemove
    public void removeTags() {
        games.forEach(game -> game.getTags().removeIf(e -> e.equals(this)));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Tag tag)) return false;
        return getId() != null && getId().equals(tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Tag.class);
    }
}