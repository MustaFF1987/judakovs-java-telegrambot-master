package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    // Создание нового пользователя:
    User createUser(User user);

    // Поиск пользователя по идентификатору:
    User getUserById(int userId);

    // Поиск пользователя по имени пользователя:
    User getUserByUsername(String username);

    // Обновление информации о пользователе:
    User updateUser(int userId, User updatedUser);

    // Удаление пользователя:
    @Transactional
    void deleteUser(int userId);

    // Получение списка всех пользователей:
    List<JpaUser> getAllUsers();

    // Добавление пользователя в чат:
    User addUserToChat(int userId, Chat chat);


    void setUserRole(Long chatId, String role);
}
