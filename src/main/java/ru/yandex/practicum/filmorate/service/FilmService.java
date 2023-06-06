package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    private final GenreService genreService;
    private final LocalDate date = LocalDate.of(1895, 12, 28);


    public Film add(Film film) {
        validate(film);
        Film filmWithId = filmStorage.add(film);
        if (film.getGenres() != null) {
            genreService.addFilmsGenre(film.getId(), film.getGenres());
        }
        log.info("Добавлен фильм под названием " + film.getName());
        return filmWithId;
    }

    public Optional<Film> put(Film film) {
        getFilmById(film.getId());
        if (film.getGenres() != null) {
            genreService.deleteFilmsGenre(film.getId());
            genreService.addFilmsGenre(film.getId(), film.getGenres());
        }
        log.info("Фильм " + film.getName() + " под номерам ID - " + film.getId() + " обновлен");
        return filmStorage.put(film);
    }

    public List<Film> get() {
        List<Film> films = filmStorage.get();
        genreService.fillFilmsGenres(films);
        log.info("Получен список фильмов");
        return films;
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getFilmById(id);
        genreService.fillFilmsGenres(List.of(film));
        log.info("Запрошен фильм под ID {}", id);
        return film;
    }

    public List<Film> getPopular(Long count) {
        List<Film> films = filmStorage.getPopular(count);
        genreService.fillFilmsGenres(films);
        log.info("Получен список популярных фильмов");
        return films;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(date)) {
            log.info("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + date);
            throw new ValidationException("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + date);
        }
    }
}
