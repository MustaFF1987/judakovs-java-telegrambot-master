package ebe.P_Judakov.s.JAVABOT.service.jpa;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ebe.P_Judakov.s.JAVABOT.controller.CombinedController;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.SubscribedChannel;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.EmptyBot;
import ebe.P_Judakov.s.JAVABOT.repository.interfaces.SubscribedChannelRepository;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class TelegramBotService extends TelegramLongPollingBot implements ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService {

    @Qualifier("telegramBotService")
    private SubscribedChannelRepository subscribedChannelRepository;

    // переменная для хранения ввода с клавиатуры
    private ReplyKeyboardMarkup keyboardMarkup;
    private UserService userService;

    private CombinedController combinedController;
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);

    public void setCombinedController(CombinedController combinedController) {
        this.combinedController = combinedController;
    }

    public void setUserService(JpaUserService userService) {
        this.userService = userService;
    }

    private void handleSetUserRole(Long chatId, String role) {
        String responseText = "Введите роль для установки:";
        try {
            sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        userService.setUserRole(chatId, role);
    }

    //переменная состояния
    private Map<Long, String> userState = new HashMap<>();
    @Override
    public void onUpdateReceived(Update update) {
        // Логируем приходящие обновления
        LOGGER.info("Received update: {}", update);
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
                        String responseText = "Список доступных команд: /start, /help, /stop, /getCustomizable";
                        sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);

                    } else if ("Subscribe".equals(text)) {
                        SubscriptionManager.subscribe(chatId);
                        String responseText = "Вы подписались на уведомления от бота.";
                        sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);
                        // Убираем кнопку "Subscribe" из клавиатуры
                        keyboardMarkup = removeSubscribeButton(keyboardMarkup);
                        // Добавляем пустого бота в чат
                        addEmptyBotToChat(chatId.toString());

                    } else if ("Unsubscribe".equals(text)) {
                        SubscriptionManager.unsubscribe(chatId);
                        String responseText = "Вы отписались от уведомлений от бота.";
                        sendTextMessageWithKeyboard(chatId, responseText, keyboardMarkup);

                    } else if ("SetUserRole".equals(text)) {
                        handleSetUserRole(chatId, "USER");
                    } else if ("SetAdminRole".equals(text)) {
                        handleSetUserRole(chatId, "ADMIN");
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
                        sendTextMessageWithKeyboard(chatId, responseText.toString(), keyboardMarkup);

                    } else if (text.startsWith("/getCustomizable")) {
                        // Проверяем, находится ли пользователь в режиме ожидания тикера акции
                        if (userState.containsKey(chatId) && userState.get(chatId).equals("AWAITING_TICKER")) {
                            // Пользователь ввел тикер, обрабатываем запрос
                            String stockTicker = getStockTickerFromMessage("Введите тикер акции:");
                            if (stockTicker != null) {
                                // Вызываем новый метод для обработки запроса к API
                                String response = getCustomizable(text);
                                // Обработка ответа от API
                                if (response != null) {
                                    // Отправляем ответ пользователю
                                    sendTextMessageWithKeyboard2(update, response, keyboardMarkup, userState);
                                }
                                // Сбрасываем состояние пользователя
                                userState.remove(chatId);
                            } else {
                                // Тикер не был извлечен, запрашиваем его снова
                                sendTextMessageWithKeyboard2(update, "Тикер акции не найден. Введите тикер акции:", keyboardMarkup, userState);
                            }
                        } else {
                            // Пользователь еще не ввел тикер, отправляем ему запрос
                            sendTextMessageWithKeyboard2(update, "Введите тикер акции:", keyboardMarkup, userState);
                            // Устанавливаем состояние пользователя в режим ожидания тикера
                            userState.put(chatId, "AWAITING_TICKER");
                        }
                    } else {
                        // В этом месте вы можете добавить свою логику обработки текстовых сообщений
                        // После формирования ответа отправляем его пользователю
                        String[] stockData = text.split(";"); // Предполагаем, что данные акции разделены символом ";"
                        StringBuilder response = new StringBuilder();
                        for (String data : stockData) {
                            response.append(data.trim()).append("\n"); // Добавляем каждый показатель акции на новой строке
                        }
                        // Отправляем ответ пользователю
                        sendTextMessageWithKeyboard2(update, response.toString(), keyboardMarkup, userState);

                    }
                } catch (TelegramApiException e) {
                    LOGGER.error("Error processing Telegram API request", e);
                    throw new RuntimeException(e);
                }
            } else {
                // Без клавиатуры
                try {
                    processIncomingMessage(update);
                } catch (TelegramApiException e) {
                    LOGGER.error("Error processing Telegram API request", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void sendTextMessageWithKeyboard2(Update update, String text, ReplyKeyboardMarkup keyboardMarkup, Map<Long, String> userState) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            try {
                if (text.startsWith("/getCustomizable")) {
                    // Проверяем, находится ли пользователь в режиме ожидания тикера акции
                    if (userState.containsKey(chatId) && userState.get(chatId).equals("AWAITING_TICKER")) {
                        // Пользователь ввел тикер, обрабатываем запрос
                        String stockTicker = getStockTickerFromMessage("Введите тикер акции");
                        if (stockTicker != null) {
                            // Вызываем новый метод для обработки запроса к API
                            String response = getCustomizable(stockTicker);
                            // Обработка ответа от API
                            if (response != null) {
                                // Отправляем ответ пользователю
                                sendTextMessageWithKeyboard2(chatId, response, keyboardMarkup);
                            }
                            // Сбрасываем состояние пользователя
                            userState.remove(chatId);
                        } else {
                            // Тикер не был извлечен, запрашиваем его снова
                            sendTextMessageWithKeyboard2(chatId, "Тикер акции не найден. Введите тикер акции:", keyboardMarkup);
                        }
                    } else {
                        // Пользователь еще не ввел тикер, отправляем ему запрос
                        sendTextMessageWithKeyboard2(chatId, "Введите тикер акции:", keyboardMarkup);
                        // Устанавливаем состояние пользователя в режим ожидания тикера
                        userState.put(chatId, "AWAITING_TICKER");
                    }
                    // Обработка введенного тикера
                    String stockTicker = text.substring("/getStock ".length()).trim();
                    int userId = getUserIdFromMessage(text); // извлекаем userId из текста команды
                    if (combinedController !=null){
                        try {
                            ResponseEntity<String> response = combinedController.getStockInfoCommand(chatId, text, userId);
                            sendTextMessageWithKeyboard(chatId, response.getBody(), keyboardMarkup);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                            sendTextMessageWithKeyboard(chatId, "Ошибка при получении информации об акции", keyboardMarkup);
                        }}

                    // Отправляем сообщение о котировках в чат
                    String stockQuote = "Текущие котировки: $100 за акцию"; // временное решение
                    sendStockQuoteToChat(chatId.toString(), stockQuote);

                } else {
                    // В этом месте вы можете добавить свою логику обработки текстовых сообщений
                    // После формирования ответа отправляем его пользователю
                    String[] stockData = text.split(";"); // Предполагаем, что данные акции разделены символом ";"
                    StringBuilder response = new StringBuilder();
                    for (String data : stockData) {
                        response.append(data.trim()).append("\n"); // Добавляем каждый показатель акции на новой строке
                    }
                    // Отправляем ответ пользователю
                    sendTextMessageWithKeyboard2(chatId, response.toString(), keyboardMarkup);
                }

            } catch (TelegramApiException e) {
                // Обработка ошибок при отправке сообщения
                LOGGER.error("Ошибка при отправке сообщения", e);
            }
        } else {
            // Логика обработки, если нет текстового сообщения
        }
    }

    // Метод для добавления пустого бота в чат
    public void addEmptyBotToChat(String chatId) {
        // токен пустого бота
        String emptyBotToken = "6553161657:AAGK4LG72f3lDBcJWW-SUBTodrM_2HBvsUc";

        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            TelegramLongPollingBot emptyBot = new EmptyBot(emptyBotToken);
            telegramBotsApi.registerBot(emptyBot);

            // Добаление пустого бота в чат
            sendTextMessageWithKeyboard(Long.parseLong(chatId), "Пустой бот добавлен в чат.", keyboardMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок при добавлении пустого бота
        }
    }

    // Метод для отправки сообщений о котировках акций в чат:
    public void sendStockQuoteToChat(String chatId, String stockQuote) {
        try {
            sendTextMessageWithKeyboard(Long.parseLong(chatId), stockQuote, keyboardMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок при отправке сообщения
        }
    }


    //  метод removeSubscribeButton для удаления кнопки "Подписаться" из клавиатуры.
    private ReplyKeyboardMarkup removeSubscribeButton(ReplyKeyboardMarkup keyboardMarkup) {
        List<KeyboardRow> keyboard = keyboardMarkup.getKeyboard();

        // Перебираем строки клавиатуры и удаляем кнопку "Subscribe"
        for (KeyboardRow row : keyboard) {
            row.removeIf(button -> "Subscribe".equals(button.getText()));
        }
        return keyboardMarkup;
    }



    private void sendTextMessageWithKeyboard2(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        // Устанавливаем клавиатуру в сообщение
        message.setReplyMarkup(keyboardMarkup);

        Message sentMessage = execute(message);
        // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
        System.out.println("Сообщение успешно отправлено: " + sentMessage);
    }

    // Метод для извлечения тикера из текста сообщения
    private String getStockTickerFromMessage(String text) {
        try {
            // Regex выражение для извлечения тикера из текста сообщения
            Pattern pattern = Pattern.compile("/getStock\\s+(\\S+)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                // Получаем найденное значение тикера
                return matcher.group(1);
            }
        } catch (Exception e) {
            // Обработка ошибок
            e.printStackTrace();
        }
        return null;
    }

    private final OkHttpClient client = new OkHttpClient();


    public String getCustomizable(String text) {
        try {
            Request request = new Request.Builder()
                    .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=TSLA")
                    .get()
                    .addHeader("X-RapidAPI-Key", "4dfa492779msh47fb50b07bc7c09p11ff37jsn1c0c729af2c0")
                    .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();

            // Логируем запрос
            LOGGER.info("Выполнен запрос к API. URL: {}, Метод: GET", request.url());

            // Обработка ответа
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Логируем успешный ответ
                LOGGER.info("Получен успешный ответ от API. Тело ответа: {}", responseBody);

                return responseBody;
            } else {
                // Логируем ошибку
                LOGGER.error("Ошибка при выполнении запроса. Код ошибки: {}, Сообщение: {}", response.code(), response.message());
                return null;
            }
        } catch (Exception e) {
            // Логируем исключение
            LOGGER.error("Ошибка при выполнении запроса", e);
            return null;
        }
    }


    private int getUserIdFromMessage(String text) {
        try {
            // Regex выражение для извлечения userId из текста команды
            Pattern pattern = Pattern.compile("/getStock\\s+(\\d+)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                // Получаем найденное значения userId
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