package com.khomsi.grid.additional.genre;

import com.khomsi.grid.additional.genre.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

