package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 1L;


    @Override
    public Optional<User> add(@RequestBody User user) {
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
}

