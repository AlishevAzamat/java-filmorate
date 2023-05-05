package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public Optional<User> add(@RequestBody @Valid User user) throws ValidationException {
        return userService.add(user);
    }

    @PutMapping(path = "/users")
    public Optional<User> put(@RequestBody @Valid User user) throws Exception {
        return userService.put(user);
    }

    @PutMapping(path = "/users/{id}/friends/{friendId}")
    public String addFriends(@PathVariable(value = "id") Long id,
                             @PathVariable(value = "friendId") Long friendId) {
        userService.addFriends(id, friendId);
        return "Пользователь добавлен в друзья";
    }

    @GetMapping(path = "/users")
    public Collection<User> get() {
        return userService.get();
    }

    @GetMapping(path = "/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getUserByID(id);
    }

    @GetMapping(path = "users/{id}/friends")
    public List<User> getFriends(@PathVariable(value = "id") Long id) {
        return userService.getFriends(id);
    }

    @GetMapping(path = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(value = "id") Long id, @PathVariable(value = "otherId") Long otherID) {
        return userService.getCommonFriends(id, otherID);
    }

    @DeleteMapping(path = "/users/{id}/friends/{friendId}")
    public String deleteFriends(@PathVariable(value = "id") Long id,
                                @PathVariable(value = "friendId") Long friendId) {
        userService.deleteFriends(id, friendId);
        return "Пользователь удален из друзей";
    }

}
