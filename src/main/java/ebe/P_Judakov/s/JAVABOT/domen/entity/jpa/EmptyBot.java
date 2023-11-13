package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EmptyBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;

    public EmptyBot(String botToken) {
        this.botToken = botToken;
        this.botUsername = "PAYU_Empty_Bot"; // имя пустого бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Пустая реализация, поскольку бот не обрабатывает входящие сообщения
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}