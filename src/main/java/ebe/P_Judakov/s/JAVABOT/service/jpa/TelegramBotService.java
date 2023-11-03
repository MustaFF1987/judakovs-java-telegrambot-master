package ebe.P_Judakov.s.JAVABOT.service.jpa;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;


public class TelegramBotService extends TelegramLongPollingBot implements ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService {

    // Метод для обработки входящего обновления от Telegram API
    @Override
    public void onUpdateReceived(Update update) {
        // Метод для обработки входящих сообщений и команд от пользователей.
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if ("/start".equals(text)) {
                // Отправляем ответное сообщение
                try {
                    sendTextMessage(update.getMessage().getChatId(),
                    "Привет! Я ваш Telegram бот для работы с котировками на бирже.");
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            onUpdateReceived(update); // Перенаправляем каждое обновление на метод onUpdateReceived
        }
    }

    @Override
    public void onUpdateReceived(org.hibernate.sql.Update update) {

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

    // Вызывается при регистрации бота в Telegram.
    // Этот метод вызывается автоматически библиотекой TelegramBots
    // после успешной регистрации бота и позволяет выполнять дополнительные действия или настройки в момент регистрации.
    @Override
    public void onRegister() {
        super.onRegister();
    }

    // метод для инициализации бота
    @Override
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            System.out.println("Бот зарегистрирован и готов к работе.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок при регистрации бота
        }
    }

    @Override
    public Message execute(SendMessage message) {
        try {
            return execute(message); // Отправка сообщения и возвращение результата
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок при отправке сообщения
            System.out.println("Ошибка ри отправке сообщения");
            return null; // Возвращаем null в случае ошибки
        }
    }

    // Метод для отправки текстовых сообщений
    public void sendTextMessage(Long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        Message sentMessage = execute(message);
        // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
        System.out.println("Сообщение успешно отправлено: " + sentMessage);
    }

    @Override
    public void processIncomingMessage(org.hibernate.sql.Update update) {
    }

    @Override
    public void processCommand(org.hibernate.sql.Update update) {
    }



    // Метод для обработки входящих сообщений
    @Override
    public void processIncomingMessage(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            Long chatId = message.getChatId();

            // Ваш код для обработки текстовых сообщений
            String responseText = "Привет! Вы написали: " + text;

            // Отправка ответа пользователю
            sendTextMessage(chatId, responseText);
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Обработка сообщений с фотографиями
            Message message = update.getMessage();
            String caption = message.getCaption();
            Long chatId = message.getChatId();

            // Ваш код для обработки сообщений с фотографиями
            String responseText = "Вы отправили фотографию с подписью: " + caption;

            // Отправка ответа пользователю
            sendTextMessage(chatId, responseText);
        }
    }

    // Метод для обработки команд от пользователя
    @Override
    public void processCommand(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.startsWith("/start")) {
                // Обработка команды /start
                String responseText = "Привет! Вы запустили бота.";
                sendTextMessage(chatId, responseText);
            } else if (text.startsWith("/help")) {
                // Обработка команды /help
                String responseText = "Список доступных команд: /start, /help";
                sendTextMessage(chatId, responseText);
            } else {
                // Обработка неизвестной команды
                String responseText = "Неизвестная команда. Используйте /help для получения списка команд.";
                sendTextMessage(chatId, responseText);
            }
        }
    }


}