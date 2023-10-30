package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaMessageService implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaMessageService.class);

    @Override
    public Message sendMessage(int chatId, int userId, String text) {
        return null;
    }

    @Override
    public List<Message> getMessagesInChat(int chatId) {
        return null;
    }

    @Override
    public Message getMessageById(int messageId) {
        return null;
    }

    @Override
    public void deleteMessage(int messageId) {

    }

    @Override
    public Message getLastMessageInChat(int chatId) {
        return null;
    }
}
