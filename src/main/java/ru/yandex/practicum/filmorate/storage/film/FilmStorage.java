package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film add(Film film);

    Optional<Film> put(Film film);

    List<Film> get();

    Film getFilmById(Long id);

    List<Film> getPopular(Long count);
}
