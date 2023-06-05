package ru.yandex.practicum.filmorate.controller.film;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.film.LikeService;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PutMapping(path = "/films/{id}/like/{userId}")
    public String addLike(@PathVariable(value = "id") Long filmId, @PathVariable(value = "userId") Long userId) {
        likeService.addLike(filmId, userId);
        return "Пользователь поставил лайк";
    }

    @DeleteMapping(path = "/films/{id}/like/{userId}")
    public String deleteLike(@PathVariable(value = "id") Long filmId, @PathVariable(value = "userId") Long userId) {
        likeService.deleteLike(filmId, userId);
        return "Пользователь удалил лайк";
    }
}
