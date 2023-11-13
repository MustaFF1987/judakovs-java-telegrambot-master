package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;


import java.util.Optional;


public interface UserRepository extends JpaRepository<JpaUser, Long> {

        Optional<JpaUser> findById(int userId);

        void deleteById(int userId);

        JpaUser findByChatId(Long chatId);
}

