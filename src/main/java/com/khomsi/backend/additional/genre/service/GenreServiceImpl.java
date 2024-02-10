package com.khomsi.backend.additional.genre.service;

import com.khomsi.backend.additional.genre.GenreRepository;
import com.khomsi.backend.additional.genre.model.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
}
