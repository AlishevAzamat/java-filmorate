package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private Long generatorId = 1L;

    @Override
    public Film add(@RequestBody Film film) {
        film.setId(generatorId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> put(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            return Optional.empty();
        }
        films.put(film.getId(), film);
        return Optional.of(film);
    }

    @Override
    public List<Film> get() {
        log.info("Текущее количество фильмов: {}", films.size());
        return (List<Film>) films;
    }

    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            return null;
        }
        return films.get(id);
    }

    @Override
    public List<Film> getPopular(Long count) {
        return null;
    }
}
