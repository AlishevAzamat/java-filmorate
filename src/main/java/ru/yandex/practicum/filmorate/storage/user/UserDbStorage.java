package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.Optional;

@Component("userDbStorage")
@Slf4j
@RequiredArgsConstructor
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
        user.setId(simpleJdbcInsert.executeAndReturnKey(ps).longValue());
        return Optional.of(user);
    }

    @Override
    public Optional<User> put(User user) {
        String sqlQuery = "UPDATE users SET email = ?,login = ?, name = ?, birthday = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());
        return Optional.of(user);
    }

    @Override
    public Collection<User> get() {
        String sqlQuery = "SELECT * " +
                "FROM users";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserByID(Long id) {
        String sqlQuery = "SELECT * " +
                "FROM users WHERE id = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{id}
                        , new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findFirst()
                .orElseThrow(() -> new ParameterNotFoundException("Пользователь под ID - '" + id + "' , не найден"));
    }
}
