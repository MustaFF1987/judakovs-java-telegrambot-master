package ebe.P_Judakov.s.JAVABOT.repository.mysql;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MySqlMessageRepository implements MessageRepository {

    // Интерфейс JpaRepository предоставляет базовые методы для работы с сущностью Message,
    // а затем добавлены ваши собственные методы.
    // Spring Data JPA автоматически предоставит реализацию этих методов,
    // мне не нужно писать их код явным образом.

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
