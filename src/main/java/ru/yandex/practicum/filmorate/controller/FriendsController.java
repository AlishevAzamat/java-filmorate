package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @PutMapping(path = "/users/{id}/friends/{friendId}")
    public String addFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        friendsService.addFriend(userId, friendId);
        return "Пользователь добавлен в друзья";
    }

    @DeleteMapping(path = "/users/{id}/friends/{friendId}")
    public String deleteFriends(@PathVariable(value = "id") Long id, @PathVariable(value = "friendId") Long friendId) {
        friendsService.deleteFriend(id, friendId);
        return "Пользователь удален из друзей";
    }

    @GetMapping(path = "users/{id}/friends")
    public List<User> getFriends(@PathVariable(value = "id") Long id) {
        return friendsService.getFriends(id);
    }

    @GetMapping(path = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(value = "id") Long id, @PathVariable(value = "otherId") Long otherID) {
        return friendsService.getCommonFriends(id, otherID);
    }
}
