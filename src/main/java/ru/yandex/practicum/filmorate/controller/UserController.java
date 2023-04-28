package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId = 1;


    @NotNull
    @NotBlank
    @PostMapping("/users")
    public User add(@RequestBody @Valid User user) throws ValidationException {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.info("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Пользователь не заполнил Имя");
            user.setName(user.getLogin());
        }
        user.setId(generatorId++);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь под именем " + user.getName());
        return user;
    }

    @PutMapping("/users")
    public User put(@RequestBody @Valid User user) throws Exception {
        if (!users.containsKey(user.getId())) {
            log.info("Пользователь " + user.getId() + " не найден");
            throw new Exception("Пользователь не найден");
        }
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " под номерам ID - " + user.getId() + " обновлен");
        return user;
    }

    @GetMapping("/users")
    public Collection<User> get() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }
}
