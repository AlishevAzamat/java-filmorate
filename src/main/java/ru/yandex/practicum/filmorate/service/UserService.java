package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    public Optional<User> add(User user) {
        validate(user);
        log.info("Добавлен пользователь под именем " + user.getName());
        return userStorage.add(user);
    }

    public Optional<User> put(User user) {
        getUserByID(user.getId());
        log.info("Пользователь " + user.getName() + " под номерам ID - '" + user.getId() + "' обновлен");
        return userStorage.put(user);
    }

    public Collection<User> get() {
        log.info("Получен список пользователей");
        return userStorage.get();
    }

    public User getUserByID(Long id) {
        log.info("Запрошен пользователем под ID {}", id);
        return userStorage.getUserByID(id);
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Пользователь не заполнил Имя");
            user.setName(user.getLogin());
        }
    }
}
