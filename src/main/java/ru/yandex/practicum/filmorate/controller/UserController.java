package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/users")
    public Optional<User> add(@RequestBody @Valid User user) {
        return userService.add(user);
    }

    @PutMapping(path = "/users")
    public Optional<User> put(@RequestBody @Valid User user) {
        return userService.put(user);
    }

    @GetMapping(path = "/users")
    public Collection<User> get() {
        return userService.get();
    }

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        return userService.getUserByID(id);
    }

}
