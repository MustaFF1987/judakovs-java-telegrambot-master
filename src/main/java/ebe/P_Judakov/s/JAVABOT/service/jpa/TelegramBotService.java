package ebe.P_Judakov.s.JAVABOT.service.jpa;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.SubscribedChannelRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class TelegramBotService extends TelegramLongPollingBot implements ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService {

    @Qualifier("telegramBotService")
    private SubscribedChannelRepository subscribedChannelRepository;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            // Создаем клавиатуру и кнопки
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            KeyboardRow row = new KeyboardRow();

            // Создаем кнопки "Subscribe" и "Unsubscribe"
            KeyboardButton subscribeButton = new KeyboardButton("Subscribe");
            KeyboardButton unsubscribeButton = new KeyboardButton("Unsubscribe");

            row.add(subscribeButton);
            row.add(unsubscribeButton);

            keyboardMarkup.setKeyboard(List.of(row));

            if (text.startsWith("/start")) {
                try {
                    sendWelcomeMessage(chatId, keyboardMarkup);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.startsWith("/help")) {
                // Обработка команды /help
                String responseText = "Список доступных команд: /start, /help, /stop, /addChannelSub, /listChannelSub";
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            } else if ("Subscribe".equals(text)) {
                SubscriptionManager.subscribe(chatId);
                String responseText = "Вы подписались на уведомления от бота.";
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            } else if ("Unsubscribe".equals(text)) {
                SubscriptionManager.unsubscribe(chatId);
                String responseText = "Вы отписались от уведомлений от бота.";
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            } else if (text.startsWith("/stop")) {
                try {
                    sendStopMessage(chatId, keyboardMarkup);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (text.startsWith("/addChannelSub")) {
                // Пользователь хочет добавить подписку на канал
                // Сообщение пользователю, запрашивая `channel_id`
                String responseText = "Введите `channel_id`, чтобы добавить подписку на канал:";
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
                SubscribedChannel subscribedChannel = new ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.SubscribedChannel();
                subscribedChannel.setChatId(Math.toIntExact(chatId));
                subscribedChannelRepository.save(subscribedChannel);
                subscribedChannel.setChannelTitle("Название канала");
                subscribedChannelRepository.save(subscribedChannel); // Сохранение в базе данных
            } else if (text.startsWith("/listChannelSub")) {
                // Обработка команды /listChannelSub
                List<SubscribedChannel> subscribedChannels = subscribedChannelRepository.findByChatId(chatId);
                StringBuilder responseText = new StringBuilder("Ваши подписки:\n");
                for (SubscribedChannel subscribedChannel : subscribedChannels) {
                    responseText.append(subscribedChannel.getChannelTitle()).append("\n");
                }
                sendTextMessageWithKeyboard(chatId, responseText.toString(), keyboardMarkup);
            } else {
                String responseText = "Неизвестная команда. Используйте /help для получения списка команд.";
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            }
        }
    }

    // Метод для отправки приветственного сообщения
    private void sendWelcomeMessage(Long chatId, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
        String welcomeText = "Добро пожаловать! Я ваш телеграм-бот.";
        welcomeText += " Начните взаимодействие с командой /help.";
        sendTextMessageWithKeyboard(chatId, welcomeText, keyboardMarkup);
    }

    // Метод для отправки сообщения при завершении работы с ботом
    private void sendStopMessage(Long chatId, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
        String stopText = "Вы завершили работу с ботом.";
        stopText += " До скорых встреч!";
        sendTextMessageWithKeyboard(chatId, stopText, keyboardMarkup);
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

    // Метод для отправки текстовых сообщений с клавиатурой
    public void sendTextMessageWithKeyboard(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup){
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(keyboardMarkup); // Установка клавиатуры

        Message sentMessage = execute(message);
        // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
        System.out.println("Сообщение успешно отправлено: " + sentMessage);
    }

    @Override
    public void onUpdateReceived(org.hibernate.sql.Update update) {

    }

    @Override
    public void processIncomingMessage(org.hibernate.sql.Update update) throws TelegramApiException {
    }

    // Метод для обработки входящих сообщений
    @Override
    public void processIncomingMessage(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(); // Создайте клавиатуру здесь

            if (update.getMessage().hasText()) {
                Message message = update.getMessage();
                String text = message.getText();

                // Ваш код для обработки текстовых сообщений
                String responseText = "Привет! Вы написали: " + text;

                // Отправка ответа пользователю
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            } else if (update.getMessage().hasPhoto()) {
                // Обработка сообщений с фотографиями
                Message message = update.getMessage();
                String caption = message.getCaption();

                // Ваш код для обработки сообщений с фотографиями
                String responseText = "Вы отправили фотографию с подписью: " + caption;

                // Отправка ответа пользователю
                sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
            }
        }
    }
}