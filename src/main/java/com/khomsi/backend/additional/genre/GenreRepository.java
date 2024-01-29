package com.khomsi.backend.additional.genre;


import com.khomsi.backend.additional.genre.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

