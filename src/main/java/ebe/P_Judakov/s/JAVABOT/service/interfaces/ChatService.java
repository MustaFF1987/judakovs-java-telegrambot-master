package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ChatService {

    // Создание нового чата.
    Chat createChat(Chat chat);

    // Поиск чата по идентификатору.
    Chat getChatById(int chatId);

    // Добавление пользователя в чат.
    Chat addUserToChat(int chatId, User user);

    // Удаление чата.
    void deleteChat(int chatId);

    // Получение списка пользователей в чате.
    List<User> getUsersInChat(int chatId);


}
