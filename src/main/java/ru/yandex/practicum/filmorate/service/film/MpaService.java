package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaService {

    private final MpaStorage mpaStorage;

    public List<Mpa> getAll() {
        log.info("Получен список рейтингов");
        return mpaStorage.getAll();
    }

    public Mpa getMpaById(Long id) {
        log.info("Рейтинг под ID - '" + id + "' , не найден");
        return mpaStorage.getMpaById(id);
    }
}
