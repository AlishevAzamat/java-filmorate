package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Optional<User> add(User user);

    Optional<User> put(User user);

    Collection<User> get();

    User getUserByID(Long id);
}
