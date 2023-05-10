package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping(path = "/films")
    public Film add(@RequestBody @Valid Film film) {
        return filmService.add(film);
    }

    @PutMapping(path = "/films")
    public Film put(@RequestBody @Valid Film film) {
        return filmService.put(film);
    }

    @PutMapping(path = "/films/{id}/like/{userId}")
    public String addLike(@PathVariable(value = "id") Long filmId,
                          @PathVariable(value = "userId") Long userId) {
        filmService.addLike(filmId, userId);
        return "Пользователь поставил лайк";
    }

    @GetMapping(path = "/films")
    public Collection<Film> get() {
        return filmService.get();
    }

    @GetMapping(path = "films/{id}")
    public Optional<Film> getFilmById(@PathVariable(value = "id") Long id) {
        return filmService.getFilmById(id);
    }

    @DeleteMapping(path = "/films/{id}/like/{userId}")
    public String deleteLike(@PathVariable(value = "id") Long filmId,
                             @PathVariable(value = "userId") Long userId) {
        filmService.deleteLike(filmId, userId);
        return "Лайк удален";
    }

    @GetMapping(path = "/films/popular")
    public List<Film> getSortFilms(@RequestParam(defaultValue = "10") Long count) {
        return filmService.getSortFilm(count);
    }
}
