package com.khomsi.backend.additional.genre.service;

import com.khomsi.backend.additional.genre.GenreRepository;
import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Genre with id " + id + " is not found."));
    }

    @Override
    public void saveGenreToDb(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Genre genre) {
        genreRepository.delete(genre);
    }
    @Override
    public boolean isGenreNameExistsIgnoreCase(String name) {
        return genreRepository.findByNameIgnoreCase(name).isPresent();
    }
}
