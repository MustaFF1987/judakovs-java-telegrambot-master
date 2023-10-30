package ebe.P_Judakov.s.JAVABOT.service.jpa;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyTelegramBotService extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Метод для обработки входящих сообщений и команд от пользователей.

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if ("/start".equals(text)) {
                // Отправляем ответное сообщение
                sendTextMessage(update.getMessage().getChatId(), "Привет! Я ваш Telegram бот для работы с котировками на бирже.");
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Имя бота, зарегистрированный в Telegram
        return "PA_YU_Unikorpa_Telegram_Bot";
    }

    @Override
    public String getBotToken() {
        // Токен бота, который получили при регистрации в Telegram
        return "6669687693:AAGVbVs_AHL22m0w-7rjPW8_h_alNLV6jBo";
    }

    // Метод для отправки текстовых сообщений
    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);


        try {
            Message sentMessage = execute(message);
            // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
            System.out.println("Сообщение успешно отправлено: " + sentMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок отправки сообщения
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }
}