package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaChat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.ChatRepository;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.UserRepository;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ebe.P_Judakov.s.JAVABOT.domen.entity.role.Role;
import java.util.List;
import java.util.Optional;


@Service
public class JpaUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserService.class);

    private  UserRepository userRepository;

    private ChatRepository chatRepository;

    @Autowired
    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        JpaUser jpaUser = (JpaUser) user;
        // создания нового пользователя
        return userRepository.save(jpaUser);
    }

    @Override
    public User getUserById(int userId) {
        Optional<JpaUser> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> (User) user).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        // Ваша логика получения пользователя по имени
        return null;
    }

    @Override
    public User updateUser(int userId, User updatedUser) {
        Optional<JpaUser> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setRole(updatedUser.getRole());
            userRepository.save(user);
        });
        return userOptional.map(user -> (User) user).orElse(null);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<JpaUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUserToChat(int userId, Chat chat) {
        Optional<JpaUser> userOptional = userRepository.findById(userId);
        Optional<JpaChat> chatOptional = chatRepository.findById(chat.getId());

        if (userOptional.isPresent() && chatOptional.isPresent()) {
            JpaUser user = userOptional.get();
            JpaChat updatedChat = chatOptional.get();

            // Добавления пользователя к чату
            updatedChat.addUser(user);
            chatRepository.save(updatedChat);

            return user;
        }
        return null; // Обработка случая, если пользователь или чат не существуют
    }

    public void createNewUser(Long chatId, String username, Role role) {
        // Создание пользователя с указанной ролью
        JpaUser newUser = new JpaUser();
        newUser.setChatId(chatId);
        newUser.setUsername(username);
        newUser.setRole(role);
        // Сохранение пользователя в базе данных
        userRepository.save(newUser);
    }

    // метод для установки роли пользователю
    public void setUserRole(Long chatId, String role) {
        // Получаем пользователя по chatId
        JpaUser user = userRepository.findByChatId(chatId);

        // Установите роль пользователю
        Role userRole = new Role(role);
        user.setRole(userRole);

        // Сохраняем обновленного пользователя в базе данных
        userRepository.save(user);
    }
}
