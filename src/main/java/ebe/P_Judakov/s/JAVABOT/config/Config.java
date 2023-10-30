package ebe.P_Judakov.s.JAVABOT.config;

import ebe.P_Judakov.s.JAVABOT.domen.database.interfaces.Database;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.ChatRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.MessageRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.UserRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlChatRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlMessageRepository;
import ebe.P_Judakov.s.JAVABOT.repository.mysql.MySqlUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
