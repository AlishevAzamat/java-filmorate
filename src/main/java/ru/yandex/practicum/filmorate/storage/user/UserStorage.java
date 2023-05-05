package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Optional<User> add(@RequestBody @Valid User user) throws ValidationException;

    Optional<User> put(@RequestBody @Valid User user) throws Exception;

    Collection<User> get();

    Optional<User> getUserByID(Long id);
}
