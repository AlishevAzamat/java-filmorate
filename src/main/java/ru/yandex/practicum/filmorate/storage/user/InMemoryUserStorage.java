package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 1L;


    @Override
    public Optional<User> add(@RequestBody @Valid User user) throws ValidationException {
        validate(user);
        user.setId(generatorId++);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь под именем " + user.getName());
        return Optional.of(user);
    }

    @Override
    public Optional<User> put(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.info("Пользователь " + user.getId() + " не найден");
            throw new IncorrectParameterException("Пользователь не найден");
        }
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " под номерам ID - " + user.getId() + " обновлен");
        return Optional.of(user);
    }

    @Override
    public Collection<User> get() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @Override
    public Optional<User> getUserByID(Long id) {
        if (!users.containsKey(id)) {
            log.info("Пользователь под ID-" + id + " , не найден");
            throw new IncorrectParameterException("Пользователь под ID-" + id + " , не найден");
        }
        log.info("Запрошен пользователем под ID {}", id);
        return Optional.of(users.get(id));
    }

    private void validate(User user) {
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
    }
}

