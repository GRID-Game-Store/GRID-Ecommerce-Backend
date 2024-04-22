package com.khomsi.backend.additional.genre.service;


import com.khomsi.backend.additional.genre.model.entity.Genre;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre getGenreById(Long id);

    void saveGenreToDb(Genre genre);
    @Transactional
    void deleteGenre(Genre genre);
}
