package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {

    @Autowired
    FilmController filmController;

    @Test
    @DisplayName("Создание фильма (проверка даты публикации фильма)")
    void addFilms_errorChecking() throws ValidationException {
        Film film = new Film();
        film.setName("");
        film.setDescription("Тест");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(10);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.add(film);
        });
        assertEquals("Дата публикации фильма раньше положенного, фильм должен быть опубликован " +
                        "не раньше чем - 1895-12-28"
                , exception.getMessage());
    }
}