package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;
import java.util.List;

public interface GenreStorage {

    List<Genre> getAll();

    Genre getGenreById(Long id);

    void addFilmsGenre(Long filmId, LinkedHashSet<Genre> genres);

    void loadGenres(List<Film> films);

    void deleteFilmsGenre(Long filmId);
}
