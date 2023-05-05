package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private Long like = 1L;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Optional<Film> add(Film film) throws ValidationException {
        return filmStorage.add(film);
    }

    public Optional<Film> put(Film film) throws Exception {
        return filmStorage.put(film);
    }

    public Collection<Film> get() {
        return filmStorage.get();
    }

    public Optional<Film> getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new IncorrectParameterException(""));
        User user = userStorage.getUserByID(userId)
                .orElseThrow(() -> new IncorrectParameterException(""));
        Set<Long> userLikes = film.getLikes();
        if (userLikes.contains(userId)) {
            log.info("Лайк уже был поставлен пользователем {}", user.getName());
            throw new IncorrectParameterException("Лайк уже был поставлен пользователем " + user.getName());
        }
        userLikes.add(user.getId());
        log.info("Пользователь {} поставил лайк на фильм {}", user.getName(), film.getName());
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new IncorrectParameterException(""));
        User user = userStorage.getUserByID(userId)
                .orElseThrow(() -> new IncorrectParameterException(""));
        Set<Long> userLikes = film.getLikes();
        if (!userLikes.contains(userId)) {
            log.info("Лайк не был поставлен пользователем {}", user.getName());
            throw new IncorrectParameterException("Лайк не был поставлен пользователем " + user.getName());
        }
        userLikes.remove(userId);
        log.info("Пользователь {} удалили лайк", user.getName());
    }

    public List<Film> getSortFilm(Long count) {
        return filmStorage.get().stream()
                .sorted(Comparator.comparingInt(f -> f.getLikes().size() * (-1)))
                .limit(count)
                .collect(Collectors.toList());
    }
}
