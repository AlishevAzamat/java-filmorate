package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@Builder
public class Film {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @PositiveOrZero
    private Integer duration;
    @NotNull
    Mpa mpa;
    LinkedHashSet<Genre> genres;
}