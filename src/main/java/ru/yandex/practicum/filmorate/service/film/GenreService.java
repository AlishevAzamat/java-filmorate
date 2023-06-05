package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreStorage genreStorage;

    public List<Genre> getAll() {
        log.info("Получаен список жанров");
        return genreStorage.getAll();
    }

    public Genre getGenreById(Long id) {
        log.info("Жанр под ID - '" + id + "' не найден");
        return genreStorage.getGenreById(id);
    }

    public void addFilmsGenre(Long filmId, LinkedHashSet<Genre> genres) {
        genreStorage.addFilmsGenre(filmId, genres);
    }

    public void deleteFilmsGenre(Long filmId) {
        genreStorage.deleteFilmsGenre(filmId);
    }

    public void addGenres(List<Film> films) {
        genreStorage.loadGenres(films);
    }
}
