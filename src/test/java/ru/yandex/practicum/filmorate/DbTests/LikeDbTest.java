package ru.yandex.practicum.filmorate.DbTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.likes.LikesDbStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbTest {

    private final LikesDbStorage likesDbStorage;

    @Test
    @DisplayName("Поставить лайк")
    public void addLike_successful() {
        likesDbStorage.addLike(1L, 1L);
        likesDbStorage.addLike(2L, 1L);
    }

    @Test
    @DisplayName("Удалить лайк")
    public void deleteLike_successful() {
        likesDbStorage.deleteLike(2L, 1L);
    }
}
