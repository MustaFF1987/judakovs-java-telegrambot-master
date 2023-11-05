package ebe.P_Judakov.s.JAVABOT.service.jpa;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramBotService extends TelegramLongPollingBot implements ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.startsWith("/start")) {
                // Обработка команды /start
                sendWelcomeMessage(chatId); // Вызов метода sendWelcomeMessage()
            } else if (text.startsWith("/help")) {
                // Обработка команды /help
                String responseText = "Список доступных команд: /start, /help, /stop, /subscribe, /unsubscribe";
                try {
                    sendTextMessage(chatId, responseText);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.startsWith("/subscribe")) {
                // Обработка команды /subscribe
                SubscriptionManager.subscribe(chatId);
                String responseText = "Вы подписались на уведомления от бота.";
                try {
                    sendTextMessage(chatId, responseText);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.startsWith("/unsubscribe")) {
                // Обработка команды /unsubscribe
                SubscriptionManager.unsubscribe(chatId);
                String responseText = "Вы отписались от уведомлений от бота.";
                try {
                    sendTextMessage(chatId, responseText);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.startsWith("/stop")) {
                // Обработка команды /stop
                sendStopMessage(chatId); // Вызов метода sendStopMessage()
            } else {
                // Обработка неизвестной команды
                String responseText = "Неизвестная команда. Используйте /help для получения списка команд.";
                try {
                    sendTextMessage(chatId, responseText);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Метод для отправки приветственного сообщения
    private void sendWelcomeMessage(Long chatId) {
        String welcomeText = "Добро пожаловать! Я ваш телеграм-бот.";
        welcomeText += " Начните взаимодействие с командой /help.";
        try {
            sendTextMessage(chatId, welcomeText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для отправки сообщения при завершении работы с ботом
    private void sendStopMessage(Long chatId) {
        String stopText = "Вы завершили работу с ботом.";
        stopText += " До скорых встреч!";
        try {
            sendTextMessage(chatId, stopText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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
            System.out.println("Бот зарегистрирован и готов к работе, введите команду /start.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок при регистрации бота
        }
    }

    @Override
    public Message execute(SendMessage message) {
        try {
            return super.execute(message); // Отправка сообщения и возвращение результата
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок при отправке сообщения
            System.out.println("Ошибка при отправке сообщения");
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
    public void onUpdateReceived(org.hibernate.sql.Update update) {

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
}