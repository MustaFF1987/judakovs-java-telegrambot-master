package ebe.P_Judakov.s.JAVABOT.config;

import ebe.P_Judakov.s.JAVABOT.repository.interfaces.ChatRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.MessageRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.SubscribedChannelRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.UserRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlChatRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlMessageRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlUserRepository;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import org.hibernate.sql.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class Config {

    @Bean
    public UserRepository customerRepository() {

        return new MySqlUserRepository();
    }

    @Bean
    public ChatRepository productRepository() {

        return new MySqlChatRepository();
    }

    @Bean
    public MessageRepository messageRepository() {

        return new MySqlMessageRepository();
    }

}
