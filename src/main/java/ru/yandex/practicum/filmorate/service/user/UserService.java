package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;
    private final InMemoryUserStorage inMemoryUserStorage;


    public User add(User user) {
        if (userStorage.add(user).isEmpty()) {
            throw new ValidationException("Ошибка валидации");
        }
        log.info("Добавлен пользователь под именем " + user.getName());
        return user;
    }

    public User put(User user) {
        if (userStorage.put(user).isEmpty()) {
            log.info("Пользователь " + user.getId() + " не найден");
            throw new ParameterNotFoundException("Пользователь не найден");
        }
        log.info("Пользователь " + user.getName() + " под номерам ID - " + user.getId() + " обновлен");
        return user;
    }

    public Collection<User> get() {
        log.info("Текущее количество пользователей: {}", inMemoryUserStorage.users.size());
        return userStorage.get();
    }

    public Optional<User> getUserByID(Long id) {
        if (!inMemoryUserStorage.users.containsKey(id)) {
            log.info("Пользователь под ID-" + id + " , не найден");
            throw new ParameterNotFoundException("Пользователь под ID-" + id + " , не найден");
        }
        log.info("Запрошен пользователем под ID {}", id);
        return userStorage.getUserByID(id);
    }

    public void addFriends(Long id, Long friendId) {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new ParameterNotFoundException(""));
        User otherUser = userStorage.getUserByID(friendId).orElseThrow(() -> new ParameterNotFoundException(""));
        if (user.getId().equals(friendId)) {
            throw new IncorrectParameterException("Пользователь не может добавить самого себя");
        }
        user.getFriends().add(friendId);
        otherUser.getFriends().add(id);
        log.info("Пользователь {} добавил в друзья {}", user.getName(), otherUser.getName());
    }

    public void deleteFriends(Long id, Long friendId) {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new ParameterNotFoundException(""));
        User otherUser = userStorage.getUserByID(friendId).orElseThrow(() -> new ParameterNotFoundException(""));
        if (!user.getFriends().contains(friendId) && user.getId().equals(id)) {
            throw new IncorrectParameterException("Пользователя " + otherUser.getName() + " нет в друзьях");
        }
        user.getFriends().remove(friendId);
        otherUser.getFriends().remove(id);
        log.info("Пользователь {} удалил из друзей {}", user.getName(), otherUser.getName());
    }

    public List<User> getFriends(Long id) {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new ParameterNotFoundException(""));
        log.info("Возвращен список друзей пользователя {} ", user.getName());
        return user.getFriends().stream()
                .map(userId -> userStorage.getUserByID(userId).orElseThrow())
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long otherID) {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new ParameterNotFoundException(""));
        User otherUser = userStorage.getUserByID(otherID).orElseThrow(() -> new ParameterNotFoundException(""));
        Set<Long> userFriends = user.getFriends();
        return otherUser.getFriends().stream()
                .filter(userFriends::contains)
                .map(userId -> userStorage.getUserByID(userId).orElseThrow())
                .collect(Collectors.toList());
    }
}
