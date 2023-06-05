package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final InMemoryUserStorage inMemoryUserStorage;


    public User add(User user) {
        inMemoryUserStorage.validate(user);
        if (userStorage.add(user).isEmpty()) {
            throw new ValidationException("Ошибка валидации");
        }
        log.info("Добавлен пользователь под именем " + user.getName());
        return user;
    }

    public User put(User user) {
        getUserByID(user.getId());
        if (userStorage.put(user).isEmpty()) {
            log.info("Пользователь '" + user.getId() + "' не найден");
            throw new ParameterNotFoundException("Пользователь не найден");
        }
        log.info("Пользователь " + user.getName() + " под номерам ID - '" + user.getId() + "' обновлен");
        return user;
    }

    public Collection<User> get() {
        log.info("Получен список пользователей");
        return userStorage.get();
    }

    public User getUserByID(Long id) {
        log.info("Запрошен пользователем под ID {}", id);
        return userStorage.getUserByID(id);
    }
}
