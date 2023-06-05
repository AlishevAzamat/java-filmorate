package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbTest {

    private final MpaDbStorage mpaDbStorage;

    @Test
    @DisplayName("Получить все рейтинги")
    public void getMpa_successful() {
        assertNotNull(mpaDbStorage.getAll());
    }

    @Test
    @DisplayName("Получить рейтинг по идентификатору")
    public void getMpaById_successful() {
        Optional<Mpa> mpaOptional = Optional.ofNullable(mpaDbStorage.getMpaById(1L));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1L)
                );

        assertThrows(ParameterNotFoundException.class, () -> mpaDbStorage.getMpaById(-4L));
    }
}
