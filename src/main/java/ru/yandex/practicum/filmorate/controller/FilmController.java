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
    private int generatorId = 1;
    private final int MAX_LENGTH = 200;
    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);

    @PostMapping("/films")
    public Film add(@RequestBody @Validated Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.info("Название фильма пустое");
            throw new ValidationException("Название фильма пустое");
        }
        if (film.getDescription().length() > MAX_LENGTH) {
            log.info("Длина описания должна быть не больше " + MAX_LENGTH + " символов");
            throw new ValidationException("Длина описания должна быть не больше " + MAX_LENGTH + " символов");
        }
        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            log.info("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + MIN_DATE);
            throw new ValidationException("Дата публикации фильма раньше положенного, фильм должен быть опубликован не раньше чем - " + MIN_DATE);
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
