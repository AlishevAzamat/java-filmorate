package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    @DisplayName("Создание пользователя (проверка логина)")
    void addUser_loginErrorChecking() {
        User user = new User();
        user.setName("Тест");
        user.setLogin("Тест ТЕСТ");
        user.setEmail("Тест@mail.ru");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.add(user);
        });

        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя (проверка дня рождения)")
    void addUser_birthdayErrorChecking() {
        User user = new User();
        user.setName("Тест");
        user.setLogin("Тест");
        user.setEmail("Тест@mail.ru");
        user.setBirthday(LocalDate.of(2500, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.add(user);
        });

        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }
}