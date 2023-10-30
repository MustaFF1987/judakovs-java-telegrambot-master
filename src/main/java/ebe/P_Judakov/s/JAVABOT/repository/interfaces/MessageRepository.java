package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;

import java.util.Date;
import java.util.List;

public interface MessageRepository {


    // Поиск сообщений, отправленных определенным пользователем:
    // Этот метод вернет список сообщений, отправленных указанным пользователем.

    List<Message> findByUser(User user);

    // Поиск сообщений в определенном чате:
    // Этот метод вернет список сообщений, относящихся к указанному чату.

    List<Message> findByChat(Chat chat);

    // Поиск сообщений, отправленных в определенный период времени:
    // Этот метод вернет список сообщений, отправленных в указанный период времени.

    List<Message> findByDateBetween(Date startDate, Date endDate);

    // Получение последнего сообщения в чате:
    // Этот метод вернет последнее сообщение в указанном чате, сортированное по дате в убывающем порядке.

    Message findTopByChatOrderByDateDesc(Chat chat);

    // Получение количества сообщений в чате:
    // Этот метод вернет общее количество сообщений в указанном чате.

    int countByChat(Chat chat);


}
