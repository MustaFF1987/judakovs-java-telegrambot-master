package ebe.P_Judakov.s.JAVABOT.scheduler;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.Article;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.SubscribedChannelRepository;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import ebe.P_Judakov.s.JAVABOT.service.jpa.ApiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleExecutor extends TelegramLongPollingBot {

    @Autowired
    SubscribedChannelRepository subscribedChannelRepository;

    @Autowired
    ApiClientService apiClientService;



    @Scheduled(fixedDelayString = "PT05M") // Каждые 5 мин
    public void sendScheduledNotifications() {
        // Получаем список подписанных групп и отправляем уведомления о новых статьях
        List<SubscribedChannel> subscribedChannels = subscribedChannelRepository.findAll();
        for (SubscribedChannel channel : subscribedChannels) {
            // Получаем новые статьи с API
            List<Article> newArticles = apiClientService.getNewArticles(channel.getLastArticleId());

            // Отправляем уведомления о новых статьях подписчикам
            for (Article article : newArticles) {
                sendNotification((long) channel.getChatId(), article.getTitle());
            }

            // Обновляем lastArticleId для группы
            if (!newArticles.isEmpty()) {
                channel.setLastArticleId(newArticles.get(newArticles.size() - 1).getId());
                subscribedChannelRepository.save(channel);
            }
        }
    }

    // Метод для отправки уведомления
    private void sendNotification(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
    }
    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}

