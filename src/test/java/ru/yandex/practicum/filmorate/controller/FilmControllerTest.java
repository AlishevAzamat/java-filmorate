package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmControllerTest {

    @Autowired
    private FilmController filmController;

    @Test
    @DisplayName("Создание фильма")
    void addFilms_errorChecking() throws ValidationException {
        Film film = new Film();
        film.setName("Тест");
        film.setDescription("Тест");
        film.setReleaseDate(LocalDate.of(2222, 1, 1));
        film.setDuration(10);

        Film add = filmController.add(film);
        assertAll(
                () -> assertEquals(1, add.getId()),
                () -> assertEquals("Тест", add.getName())
        );
    }
}