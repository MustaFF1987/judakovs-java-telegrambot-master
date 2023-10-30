package ebe.P_Judakov.s.JAVABOT.repository.mysql;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.MessageRepository;

import java.util.Date;
import java.util.List;

public class MySqlMessageRepository implements MessageRepository {

    @Override
    public List<Message> findByUser(User user) {
        return null;
    }

    @Override
    public List<Message> findByChat(Chat chat) {
        return null;
    }

    @Override
    public List<Message> findByDateBetween(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Message findTopByChatOrderByDateDesc(Chat chat) {
        return null;
    }

    @Override
    public int countByChat(Chat chat) {
        return 0;
    }
}
