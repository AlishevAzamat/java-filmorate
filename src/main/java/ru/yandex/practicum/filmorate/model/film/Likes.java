package ru.yandex.practicum.filmorate.model.film;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Likes {
    @NotNull
    Long filmId;
    @NotNull
    Long userId;
}
