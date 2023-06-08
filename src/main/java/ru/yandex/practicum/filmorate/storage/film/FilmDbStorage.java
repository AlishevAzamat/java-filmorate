package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ParameterNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component("filmDbStorage")
@Primary
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        String sqlQuery = "INSERT INTO films (name,description,release_date,duration,mpa_id) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setLong(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    @Override
    public Optional<Film> put(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ? , mpa_id = ?" +
                " WHERE id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        return Optional.of(film);
    }

    @Override
    public List<Film> get() {
        String sqlQuery = "SELECT * " +
                "FROM films as f " +
                "INNER JOIN film_mpa as m on m.mpa_id = f.mpa_id";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film getFilmById(Long id) {
        String sqlQuery = "SELECT * " +
                "FROM films as f " +
                "INNER JOIN film_mpa as m on m.mpa_id = f.mpa_id " +
                "WHERE f.id = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{id}, this::mapRowToFilm)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ParameterNotFoundException("Фильм '" + id + "' не найден"));
    }

    @Override
    public List<Film> getPopular(Long count) {
        String sqlQuery = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.mpa_id ,m.mpa_name" +
                ", m.mpa_description " +
                "FROM films as f " +
                "INNER JOIN film_mpa as m on m.mpa_id = f.mpa_id " +
                "LEFT JOIN likes as l on l.film_id = f.id " +
                "GROUP BY f.id " +
                "ORDER BY count(l.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name((resultSet.getString("name")))
                .releaseDate((resultSet.getDate("release_date")).toLocalDate())
                .description(resultSet.getString("description"))
                .duration(resultSet.getInt("duration"))
                .mpa(Mpa.builder()
                        .id(resultSet.getLong("mpa_id"))
                        .name(resultSet.getString("mpa_name"))
                        .description(resultSet.getString("mpa_description"))
                        .build())
                .genres(new LinkedHashSet<>())
                .build();
    }
}
