package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbTest {
    private final FilmDbStorage filmDbStorage;


    @Test
    @DisplayName("Поиск фильма по ID")
    public void getFilmById_successful() {
        Optional<Film> film = filmDbStorage.add(Film.builder()
                .name("ТЕСТ")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(100)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );

        Optional<Film> filmOptional = Optional.ofNullable(filmDbStorage.getFilmById(1L));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("id", 1L)
                );

        assertThrows(ParameterNotFoundException.class, () -> filmDbStorage.getFilmById(-4L));
    }

    @Test
    @DisplayName("Обновление фильма")
    public void updateFilm_successful() {
        Optional<Film> film = filmDbStorage.put(Film.builder()
                .id(1L)
                .name("ТЕСТ")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(10)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );
    }

    @Test
    @DisplayName("Список популярных фильмов")
    public void getPopularFilm_successful() {
        filmDbStorage.add(Film.builder()
                .name("ТЕСТ1")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 2, 1))
                .duration(107)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );
        filmDbStorage.add(Film.builder()
                .name("ТЕСТ2")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 2, 1))
                .duration(109)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );

        assertNotNull(filmDbStorage.getPopular(2L));
        assertEquals(1, filmDbStorage.getPopular(1L).size());
    }

    @Test
    @Order(1)
    @DisplayName("Получение всех фильмов")
    public void getAllFilms_successful() {
        assertNotNull(filmDbStorage.get());
        assertEquals(1, filmDbStorage.get().size());
    }
}
