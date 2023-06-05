package ru.yandex.practicum.filmorate.model.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class Friends {
    @NotNull
    private Long userId;
    @NotNull
    private Long friendId;
}
