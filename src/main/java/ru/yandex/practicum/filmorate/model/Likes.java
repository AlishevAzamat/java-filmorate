package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Likes {
    @NotNull
    private Long filmId;
    @NotNull
    private Long userId;
}
