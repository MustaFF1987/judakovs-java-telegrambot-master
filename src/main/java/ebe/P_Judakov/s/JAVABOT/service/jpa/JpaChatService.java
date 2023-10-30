package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaChatService implements ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaChatService.class);

    @Override
    public Chat createChat(Chat chat) {
        return null;
    }

    @Override
    public Chat getChatById(int chatId) {
        return null;
    }

    @Override
    public Chat addUserToChat(int chatId, User user) {
        return null;
    }

    @Override
    public void deleteChat(int chatId) {
    }

    @Override
    public List<User> getUsersInChat(int chatId) {
        return null;
    }
}
