package ebe.P_Judakov.s.JAVABOT.service.jpa;
import ebe.P_Judakov.s.JAVABOT.controller.CombinedController;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.SubscribedChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class TelegramBotService extends TelegramLongPollingBot implements ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService {

    @Qualifier("telegramBotService")
    private SubscribedChannelRepository subscribedChannelRepository;

    // переменная для хранения ввода с клавиатуры
    private ReplyKeyboardMarkup keyboardMarkup;

    private CombinedController combinedController;

    public void setCombinedController(CombinedController combinedController) {
        this.combinedController = combinedController;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.startsWith("/start") && keyboardMarkup == null) {
                // Инициализация клавиатуры при старте чата
                keyboardMarkup = createKeyboardMarkup();
            }

            if (keyboardMarkup != null) {
                try {
                    // Используйте клавиатуру
                    if (text.startsWith("/start")) {
                        sendWelcomeMessage(chatId, keyboardMarkup);
                    } else if (text.startsWith("/help")) {
                        // Обработка команды /help
                        String responseText = "Список доступных команд: /start, /help, /stop, /addChannelSub, /listChannelSub, /getStock";
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
                        sendStopMessage(chatId, keyboardMarkup);
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
                    processIncomingMessage(update);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Без клавиатуры
                try {
                    processIncomingMessage(update);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (text.startsWith("/getStock")) {
                int userId = getUserIdFromMessage(text); // Здесь вам нужно извлечь userId из текста команды
                try {
                    ResponseEntity<String> response = combinedController.getStockInfoCommand(chatId, userId);
                    sendTextMessageWithKeyboard(chatId, response.getBody(), keyboardMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    try {
                        sendTextMessageWithKeyboard(chatId, "Ошибка при получении информации об акции", keyboardMarkup);
                    } catch (TelegramApiException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    private int getUserIdFromMessage(String text) {
        try {
            // Регулярное выражение для извлечения userId из текста команды
            Pattern pattern = Pattern.compile("/getStock\\s+(\\d+)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                // Получение найденного значения userId
                String userIdStr = matcher.group(1);
                return Integer.parseInt(userIdStr);
            }
        } catch (NumberFormatException e) {
            // Обработка ошибки преобразования строки в число
            e.printStackTrace();
        }

        // В случае ошибки возвращаем значение по умолчанию
        return 0;
    }

    private ReplyKeyboardMarkup createKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        // Создаем строки и кнопки
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Subscribe");
        row.add("Unsubscribe");
        keyboard.add(row);

        // Устанавливаем клавиатуру в клавиатуре разметке
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    // Метод для отправки приветственного сообщения
    private void sendWelcomeMessage(Long chatId, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
        String welcomeText = "Добро пожаловать! Я ваш телеграм-бот.";
        welcomeText += " Начните взаимодействие с командой /help.";
        sendTextMessageWithKeyboard(chatId, welcomeText, keyboardMarkup);
    }


    // Метод для отправки сообщения при завершении работы с ботом с клавиатурой
    public void sendStopMessage(Long chatId, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
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
    public void sendTextMessageWithKeyboard(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        // Устанавливаем клавиатуру в сообщение
        message.setReplyMarkup(keyboardMarkup);

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
    public void processIncomingMessage(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            if (keyboardMarkup != null) {
                if (update.getMessage().hasText()) {
                    Message message = update.getMessage();
                    String text = message.getText();

                    // Ваш код для обработки текстовых сообщений
                    String responseText = "Ваше сообщение: " + text;

                    // Отправка ответа пользователю с использованием клавиатуры
                    sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
                } else if (update.getMessage().hasPhoto()) {
                    // Обработка сообщений с фотографиями
                    Message message = update.getMessage();
                    String caption = message.getCaption();

                    // Ваш код для обработки сообщений с фотографиями
                    String responseText = "Вы отправили фотографию с подписью: " + caption;

                    // Отправка ответа пользователю с использованием клавиатуры
                    sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
                }
            } else {
            }
        }
    }
}