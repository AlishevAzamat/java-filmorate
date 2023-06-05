package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.friends.FriendsDbStorage;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendsDbTest {

    private final FriendsDbStorage friendsDbStorage;


    @Test
    @DisplayName("Добавления в друзья")
    public void addFriend_successful() {
        friendsDbStorage.addFriend(2L, 4L);
    }

    @Test
    @DisplayName("Удаление из друзей")
    public void deleteFriend_successful() {
        friendsDbStorage.deleteFriend(2L, 4L);
    }

    @Test
    @DisplayName("Получение друзей")
    public void getFriends_successful() {
        friendsDbStorage.addFriend(1L, 2L);

        assertNotNull(friendsDbStorage.getFriends(1L));
    }

    @Test
    @DisplayName("Пересечение друзей")
    public void getCommonFriends_successful() {
        friendsDbStorage.addFriend(1L, 5L);
        friendsDbStorage.addFriend(2L, 5L);

        assertNotNull(friendsDbStorage.getCommonFriends(1L, 2L));

    }
}
