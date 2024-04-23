package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.additional.genre.model.entity.Genre;
import com.khomsi.backend.additional.genre.service.GenreService;
import com.khomsi.backend.main.admin.model.request.EntityEditRequest;
import com.khomsi.backend.main.admin.model.request.EntityInsertRequest;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.AdminGenreService;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminGenreServiceImpl implements AdminGenreService {
    private final GenreService genreService;

    @Override
    public AdminResponse addGenre(EntityInsertRequest entityInsertRequest) {
        String name = entityInsertRequest.name();
        checkIfGenreNameAlreadyExists(name);
        Genre genre = new Genre();
        genre.setName(name);
        genreService.saveGenreToDb(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is created!").build();
    }

    @Override
    public AdminResponse editGenre(EntityEditRequest entityEditRequest) {
        Genre genre = genreService.getGenreById(entityEditRequest.id());
        String newName = entityEditRequest.name();
        checkIfGenreNameAlreadyExists(newName);
        genre.setName(newName);
        genreService.saveGenreToDb(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is edited!").build();
    }

    @Override
    public AdminResponse deleteGenre(Long id) {
        Genre genre = genreService.getGenreById(id);
        genreService.deleteGenre(genre);
        return AdminResponse.builder().response("Genre with id " + genre.getId() + " is deleted!").build();
    }

    private void checkIfGenreNameAlreadyExists(String newGame) {
        if (genreService.isGenreNameExistsIgnoreCase(newGame)) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, "Genre with name '" + newGame + "' already exists!");
        }
    }
}
