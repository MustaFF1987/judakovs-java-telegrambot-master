package ebe.P_Judakov.s.JAVABOT.repository.interfaces;


import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Chat;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    // Поиск пользователей по имени пользователя
    // Этот метод вернет список пользователей с указанным именем пользователя:

    List<JpaUser> findByUsername(String username);

    // Поиск пользователей по имени и фамилии
    // Этот метод вернет список пользователей с указанными именем и фамилией.

    List<JpaUser> findByFirstNameAndLastName(String firstName, String lastName);

    // Поиск пользователей по чатам, в которых они участвуют
    // Этот метод вернет список пользователей, участвующих в указанных чатах.

    List<JpaUser> findByChatsIn(List<Chat> chats);

    // Получение количества пользователей
    // Этот метод вернет общее количество пользователей в базе данных.

    List<JpaUser> getAll();

    // Удаление пользователей по имени пользователя:
    // Этот метод удалит всех пользователей с указанным именем пользователя.

    void deleteByUsername(String username);

}
