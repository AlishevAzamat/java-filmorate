package ru.yandex.practicum.filmorate.service.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FriendsService {

    private final FriendsStorage friendsStorage;
    private final UserService userService;

    public void addFriend(Long userId, Long friendId) {
        User user = userService.getUserByID(userId);
        User otherUser = userService.getUserByID(friendId);
        if (user.getId().equals(friendId)) {
            throw new IncorrectParameterException("Пользователь не может добавить самого себя");
        }
        friendsStorage.addFriend(userId, friendId);
        log.info("Пользователь {} добавил в друзья {}", user.getName(), otherUser.getName());
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = userService.getUserByID(userId);
        User otherUser = userService.getUserByID(friendId);
        friendsStorage.deleteFriend(userId, friendId);
        log.info("Пользователь {} удалил из друзей {}", user.getName(), otherUser.getName());
    }

    public List<User> getFriends(Long id) {
        User user = userService.getUserByID(id);
        log.info("Возвращен список друзей пользователя {} ", user.getName());
        return friendsStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = userService.getUserByID(userId);
        User otherUser = userService.getUserByID(friendId);
        log.info("Возврощен общий список пользователей между - {} , {}", user.getName(), otherUser.getName());
        return friendsStorage.getCommonFriends(userId, friendId);
    }
}
