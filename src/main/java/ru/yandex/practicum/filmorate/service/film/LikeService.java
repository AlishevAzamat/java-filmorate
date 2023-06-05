package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.likes.LikesStorage;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {
    private final LikesStorage likesStorage;
    private final UserService userService;
    private final FilmService filmService;

    public void addLike(Long filmId, Long userId) {
        filmService.getFilmById(filmId);
        userService.getUserByID(userId);
        likesStorage.addLike(filmId, userId);
        log.info("Пользователь {} поставил лайк на фильм {}", userService.getUserByID(userId).getName(), filmService.getFilmById(filmId).getName());
    }

    public void deleteLike(Long filmId, Long userId) {
        filmService.getFilmById(filmId);
        userService.getUserByID(userId);
        likesStorage.deleteLike(filmId, userId);
        log.info("Пользователь {} удалил лайк на фильм {}", userService.getUserByID(userId).getName(), filmService.getFilmById(filmId).getName());
    }
}
