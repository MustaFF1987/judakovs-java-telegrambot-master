package ebe.P_Judakov.s.JAVABOT.repository.mysql;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.ChatRepository;

import java.util.List;

public class MySqlChatRepository implements ChatRepository {

    @Override
    public List<Chat> findByUsers(User user) {
        return null;
    }

    @Override
    public Chat findByChatId(Long chatId) {
        return null;
    }

    @Override
    public List<Message> findMessagesByChat(Chat chat) {
        return null;
    }

    @Override
    public Chat save(Chat chat) {
        return null;
    }

    @Override
    public void delete(Chat chat) {

    }

    @Override
    public int countUsersByChat(Chat chat) {
        return 0;
    }
}
