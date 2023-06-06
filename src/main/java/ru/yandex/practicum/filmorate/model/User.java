package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    @NotNull(message = "Email не может быть не задан")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть корректным")
    private String email;
    @NotBlank
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}

