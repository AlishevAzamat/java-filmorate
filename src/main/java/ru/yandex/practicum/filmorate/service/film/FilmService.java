package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final GenreService genreService;


    public Film add(Film film) {
        inMemoryFilmStorage.validate(film);
        if (filmStorage.add(film).isEmpty()) {
            throw new ValidationException("Ошибка валидации");
        }
        if (film.getGenres() != null) {
            genreService.addFilmsGenre(film.getId(), film.getGenres());
        }
        log.info("Добавлен фильм под названием " + film.getName());
        return film;
    }

    public Film put(Film film) {
        getFilmById(film.getId());
        if (film.getGenres() != null) {
            genreService.deleteFilmsGenre(film.getId());
            genreService.addFilmsGenre(film.getId(), film.getGenres());
        }
        if (filmStorage.put(film).isEmpty()) {
            log.info("Фильм " + film.getId() + " не найден");
            throw new ParameterNotFoundException("Фильм " + film.getId() + " не найден");
        }
        log.info("Фильм " + film.getName() + " под номерам ID - " + film.getId() + " обновлен");
        return film;
    }

    public List<Film> get() {
        List<Film> films = filmStorage.get();
        genreService.addGenres(films);
        log.info("Получен список фильмов");
        return films;
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getFilmById(id);
        genreService.addGenres(List.of(film));
        log.info("Запрошен фильм под ID {}", id);
        return film;
    }

    public List<Film> getPopular(Long count) {
        List<Film> films = filmStorage.getPopular(count);
        genreService.addGenres(films);
        log.info("Получен список популярных фильмов");
        return films;
    }
}
