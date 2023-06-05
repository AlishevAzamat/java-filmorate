package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbTest {
    private final UserDbStorage userStorage;

    @Test
    @DisplayName("Получить пользователя по идентификатору")
    public void getUserById_successful() {
        Optional<User> user = userStorage.add(User.builder()
                .email("ТЕСТ1@ya.ru")
                .login("ТЕСТ2")
                .name("ТЕСТ3")
                .birthday(LocalDate.of(2022, 1, 1))
                .build()
        );

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserByID(1L));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(us ->
                        assertThat(us).hasFieldOrPropertyWithValue("id", 1L)
                );

        assertThrows(ParameterNotFoundException.class, () -> userStorage.getUserByID(-4L));
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void getUser_successful() {
        Optional<User> user = userStorage.add(User.builder()
                .email("ТЕСТ4@ya.ru")
                .login("ТЕСТ5")
                .name("ТЕСТ6")
                .birthday(LocalDate.of(2022, 2, 1))
                .build()
        );
        assertNotNull(userStorage.get());
        assertEquals(2, userStorage.get().size());
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateUser_successful() {
        Optional<User> user = userStorage.put(User.builder()
                .id(1L)
                .email("ТЕСТ7@ya.ru")
                .login("ТЕСТ8")
                .name("ТЕСТ9")
                .birthday(LocalDate.of(2022, 1, 1))
                .build()
        );
    }
}
