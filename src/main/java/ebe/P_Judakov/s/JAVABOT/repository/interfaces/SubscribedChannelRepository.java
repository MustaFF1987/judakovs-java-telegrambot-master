package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribedChannelRepository extends JpaRepository<SubscribedChannel, Long> {
    List<SubscribedChannel> findByChatId(Long chatId);

}
