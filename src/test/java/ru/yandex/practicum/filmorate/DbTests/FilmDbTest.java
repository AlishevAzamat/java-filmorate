package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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
        Optional<Film> film = Optional.ofNullable(filmDbStorage.add(Film.builder()
                .name("ТЕСТ")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(100)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        ));

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
                .name("ТЕСТ123")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(2022, 2, 1))
                .duration(107)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );
        filmDbStorage.add(Film.builder()
                .name("ТЕСТ2456")
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
    @DisplayName("Получение всех фильмов")
    public void getAllFilms_successful() {
        assertNotNull(filmDbStorage.get());
        assertEquals(1, filmDbStorage.get().size());
    }

    @Test
    @DisplayName("Ошибка дубликата при создании фильма")
    public void addFilm_error() {
        filmDbStorage.add(Film.builder()
                .name("ТЕСТ1")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(107)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        );
        assertThrows(DuplicateKeyException.class, () -> filmDbStorage.add(Film.builder()
                .name("ТЕСТ1")
                .description("ТЕСТ")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(107)
                .mpa(Mpa.builder()
                        .id(2L)
                        .build())
                .build()
        ));
    }
}
