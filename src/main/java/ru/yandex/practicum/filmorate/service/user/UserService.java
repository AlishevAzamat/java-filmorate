package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Optional<User> add(User user) throws ValidationException {
        return userStorage.add(user);
    }

    public Optional<User> put(User user) throws Exception {
        return userStorage.put(user);
    }

    public Collection<User> get() {
        return userStorage.get();
    }

    public Optional<User> getUserByID(Long id) {
        return userStorage.getUserByID(id);
    }

    public void addFriends(Long id, Long friendId) throws IncorrectParameterException {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new IncorrectParameterException(""));
        User otherUser = userStorage.getUserByID(friendId).orElseThrow(() -> new IncorrectParameterException(""));
        if (user.getId().equals(friendId)) {
            throw new IncorrectParameterException("Пользователь не может добавить самого себя");
        }
        user.getFriends().add(friendId);
        otherUser.getFriends().add(id);
        log.info("Пользователь {} добавил в друзья {}", user.getName(), otherUser.getName());
    }

    public void deleteFriends(Long id, Long friendId) throws IncorrectParameterException {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new IncorrectParameterException(""));
        User otherUser = userStorage.getUserByID(friendId).orElseThrow(() -> new IncorrectParameterException(""));
        if (!user.getFriends().contains(friendId) && user.getId().equals(id)) {
            throw new IncorrectParameterException("Пользователя " + otherUser.getName() + " нет в друзьях");
        }
        user.getFriends().remove(friendId);
        otherUser.getFriends().remove(id);
        log.info("Пользователь {} удалил из друзей {}", user.getName(), otherUser.getName());
    }

    public List<User> getFriends(Long id) throws IncorrectParameterException {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new IncorrectParameterException(""));
        log.info("Возвращен список друзей пользователя {} ", user.getName());
        return user.getFriends().stream()
                .map(userId -> userStorage.getUserByID(userId).orElseThrow())
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long otherID) throws IncorrectParameterException {
        User user = userStorage.getUserByID(id).orElseThrow(() -> new IncorrectParameterException(""));
        User otherUser = userStorage.getUserByID(otherID).orElseThrow(() -> new IncorrectParameterException(""));
        Set<Long> userFriends = user.getFriends();
        return otherUser.getFriends().stream()
                .filter(userFriends::contains)
                .map(userId -> userStorage.getUserByID(userId).orElseThrow())
                .collect(Collectors.toList());
    }
}
