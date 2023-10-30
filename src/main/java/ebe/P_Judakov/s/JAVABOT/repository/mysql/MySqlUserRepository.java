package ebe.P_Judakov.s.JAVABOT.repository.mysql;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.UserRepository;

import java.util.List;

public class MySqlUserRepository implements UserRepository {

    @Override
    public List<JpaUser> findByUsername(String username) {
        return null;
    }

    @Override
    public List<JpaUser> findByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<JpaUser> findByChatsIn(List<Chat> chats) {
        return null;
    }

    @Override
    public List<JpaUser> getAll() {
        return null;
    }

    @Override
    public void deleteByUsername(String username) {

    }
}
