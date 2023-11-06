package ebe.P_Judakov.s.JAVABOT.repository.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribedChannelRepository extends JpaRepository<SubscribedChannel, Long> {
    List<SubscribedChannel> findByChatId(Long chatId);

}
