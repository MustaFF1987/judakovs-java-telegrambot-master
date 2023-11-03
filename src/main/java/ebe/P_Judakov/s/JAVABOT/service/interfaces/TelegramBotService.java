package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import org.hibernate.sql.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramBotService {

    // Обработка входящего обновления от Telegram API
    void onUpdateReceived(Update update);

    // Получение имени бота
    String getBotUsername();

    // Получение токена бота
    String getBotToken();

    void init();

    // Метод для отправки сообщения пользователю
    void sendTextMessage(Long chatId, String text) throws TelegramApiException;

    // Метод для обработки входящих сообщений
    void processIncomingMessage(Update update);

    // Метод для обработки команд от пользователя
    void processCommand(Update update);


    // Метод для обработки входящих сообщений
    void processIncomingMessage(org.telegram.telegrambots.meta.api.objects.Update update) throws TelegramApiException;

    // Метод для обработки команд от пользователя
    void processCommand(org.telegram.telegrambots.meta.api.objects.Update update) throws TelegramApiException;
}
