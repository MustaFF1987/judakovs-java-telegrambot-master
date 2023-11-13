package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import java.util.Date;
import java.util.List;

    public interface MessageRepository {

        List<Message> findByUser(User user);

        List<Message> findByChat(Chat chat);

        List<Message> findByDateBetween(Date startDate, Date endDate);

        Message findTopByChatOrderByDateDesc(Chat chat);

        int countByChat(Chat chat);
    }
