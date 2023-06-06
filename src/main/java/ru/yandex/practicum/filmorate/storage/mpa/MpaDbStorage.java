package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM film_mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMPA);
    }

    @Override
    public Mpa getMpaById(Long id) {
        String sqlQuery = "SELECT * FROM film_mpa WHERE mpa_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMPA, id).stream().findFirst()
                .orElseThrow(() -> new ParameterNotFoundException("Рейтинг под ID - '" + id + "' , не найден"));
    }

    private Mpa mapRowToMPA(ResultSet resultSet, int nowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getLong("mpa_id"))
                .name(resultSet.getString("mpa_name"))
                .description(resultSet.getString("mpa_description"))
                .build();
    }
}
