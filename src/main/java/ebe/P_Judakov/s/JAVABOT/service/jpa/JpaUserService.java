package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserService.class);

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User updateUser(int userId, User updatedUser) {
        return null;
    }

    @Override
    public void deleteUser(int userId) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User addUserToChat(int userId, Chat chat) {
        return null;
    }
}
