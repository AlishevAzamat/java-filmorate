package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface FriendsStorage {
    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getFriends(Long userId);

    List<User> getCommonFriends(Long userId, Long friendId);
}
