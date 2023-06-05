package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.film.Film;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> add(@RequestBody @Valid Film film);

    Optional<Film> put(@RequestBody @Valid Film film);

    List<Film> get();

    Film getFilmById(Long id);

    List<Film> getPopular(Long count);
}
