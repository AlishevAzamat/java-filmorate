package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();
    int generatorId = 1;
    final int LENGTH = 200;
    final LocalDate DATE = LocalDate.of(1895, 12, 28);

    @PostMapping("/films")
    public Film add(@RequestBody @Validated Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.info("Название фильма пустое");
            throw new ValidationException("Название фильма пустое");
        }
        if (film.getDescription().length() > LENGTH) {
            log.info("Длина описания должна быть не больше " + LENGTH + " символов");
            throw new ValidationException("Длина описания должна быть не больше " + LENGTH + " символов");
        }
        if (film.getReleaseDate().isBefore(DATE)) {
            log.info("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + DATE);
            throw new ValidationException("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + DATE);
        }
        if (film.getDuration() < 0) {
            log.info("Продолжительность фильма не должна быть отрицательной");
            throw new ValidationException("Продолжительность фильма не должна быть отрицательной");
        }
        film.setId(generatorId++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film put(@RequestBody @Validated Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.info("Фильм не найден");
            throw new ValidationException("Фильм не найден");
        }
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " под номерам ID - " + film.getId() + " обновлен");
        return film;
    }

    @GetMapping("/films")
    public Collection<Film> get() {
        log.info("Текущее количество фильмов: {}", films.size());
        return films.values();
    }
}
