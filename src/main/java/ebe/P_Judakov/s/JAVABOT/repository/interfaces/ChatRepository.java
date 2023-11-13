package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaChat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    // Поиск чатов, в которых участвует определенный пользователь.
    // Этот метод вернет список чатов, в которых участвует указанный пользователь.

    List<Chat> findByUsers(User user);

    // Поиск чата по его уникальному идентификатору.
    // Этот метод вернет чат с указанным уникальным идентификатором.

    Chat findByChatId(Long chatId);

    // Получение списка всех сообщений в чате.
    // Этот метод вернет список всех сообщений, относящихся к указанному чату.

    List<Message> findMessagesByChat(Chat chat);

    // Создание нового чата.
    // Этот метод позволяет создать новый чат, добавляя его в базу данных.

    Chat save(Chat chat);

    // Удаление чата.
    // Этот метод позволяет создать новый чат, добавляя его в базу данных.

    void delete(Chat chat);

    // Получение количества участников в чате:
    // Этот метод вернет количество пользователей, участвующих в указанном чате.

    int countUsersByChat(Chat chat);


    Optional<JpaChat> findById(int id);
}
