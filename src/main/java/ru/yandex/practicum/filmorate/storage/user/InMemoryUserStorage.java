package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("inMemoryUserStorage")
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 1L;


    @Override
    public Optional<User> add(@RequestBody User user) {
        try {
            validate(user);
        } catch (ValidationException e) {
            return Optional.empty();
        }
        user.setId(generatorId++);
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> put(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            return Optional.empty();
        }
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> get() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @Override
    public User getUserByID(Long id) {
        if (!users.containsKey(id)) {
            return null;
        }
        return users.get(id);
    }

    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Пользователь не заполнил Имя");
            user.setName(user.getLogin());
        }
    }
}

