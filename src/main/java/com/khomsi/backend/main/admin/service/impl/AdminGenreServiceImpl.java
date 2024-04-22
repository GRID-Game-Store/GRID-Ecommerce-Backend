package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.genre.service.GenreService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminGenreServiceImpl implements AdminGenreService {
    private final GenreService genreService;

    @Override
    public AdminResponse addGenre(EntityInsertRequest entityInsertRequest) {
        Genre genre = new Genre();
        genre.setName(entityInsertRequest.name());
        genreService.saveGenreToDb(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is created!").build();
    }

    @Override
    public AdminResponse editGenre(EntityEditRequest entityEditRequest) {
        Genre genre = genreService.getGenreById(entityEditRequest.id());
        genre.setName(entityEditRequest.name());
        genreService.saveGenreToDb(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deleteGenre(Long id) {
        Genre genre = genreService.getGenreById(id);
        genreService.deleteGenre(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is deleted!").build();
    }
}
