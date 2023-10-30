package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;

import java.util.List;

public interface MessageService {

    // Отправка сообщения в чат.
    Message sendMessage(int chatId, int userId, String text);

    // Получение всех сообщений в чате.
    List<Message> getMessagesInChat(int chatId);

    // Получение сообщения по его идентификатору.
    Message getMessageById(int messageId);

    // Удаление сообщения.
    void deleteMessage(int messageId);

    // Получение последнего сообщения в чате.
    Message getLastMessageInChat(int chatId);

}
