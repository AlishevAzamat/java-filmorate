package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friends (user_id,friend_id) VALUES (?,?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        String sqlQuery = "SELECT * " +
                "FROM users as u " +
                "INNER JOIN friends as f on u.id = f.friend_id " +
                "WHERE f.user_id = ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {
        String sqlQuery = "SELECT *" +
                "FROM users as u " +
                "INNER JOIN friends as f on u.id = f.friend_id " +
                "WHERE user_id = ? AND friend_id IN (SELECT friend_id " +
                "FROM friends " +
                "WHERE user_id = ?)";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId, friendId);
    }
}
