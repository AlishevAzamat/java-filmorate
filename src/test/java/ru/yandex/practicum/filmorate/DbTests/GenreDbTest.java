package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbTest {

    private final GenreDbStorage genreDbStorage;

    @Test
    @DisplayName("Получение всех жанров")
    public void getAllGenre_successful() {
        assertNotNull(genreDbStorage.getAll());
    }

    @Test
    @DisplayName("Получть жанр по идентификатору")
    public void getGenreById_successful() {
        Optional<Genre> genreOptional = Optional.ofNullable(genreDbStorage.getGenreById(1L));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1L)
                );

        assertThrows(ParameterNotFoundException.class, () -> genreDbStorage.getGenreById(-4L));
    }
}
