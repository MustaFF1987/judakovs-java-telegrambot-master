package ebe.P_Judakov.s.JAVABOT.controller;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.StockDataEntity;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import ebe.P_Judakov.s.JAVABOT.service.jpa.JpaUserService;
import ebe.P_Judakov.s.JAVABOT.service.jpa.StockDataService;
import io.micrometer.common.util.StringUtils;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис Сообщений.
 * Содержит бизнес-логику, относящуюся к телеграм боту.
 */

@RestController
@RequestMapping("/api-telegram-bot")
public class CombinedController {

    @Autowired
    private TelegramBotService telegramBotService;

    private final JpaUserService userService;
    private final StockDataService stockDataService;

    //private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    public CombinedController(JpaUserService userService, StockDataService stockDataService) {
        this.userService = userService;
        this.stockDataService = stockDataService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveUpdate(@RequestBody Update update) {
        telegramBotService.onUpdateReceived(update);
        return ResponseEntity.ok("Обновление получено и успешно обработано.");
    }

    @GetMapping("/start")
    public ResponseEntity<String> startBot() {
        telegramBotService.init();
        return ResponseEntity.ok("Началась инициализация бота.");
    }

    @GetMapping("/bot-username")
    public ResponseEntity<String> getBotUsername() {
        String botUsername = telegramBotService.getBotUsername();
        return ResponseEntity.ok("Username бота: " + botUsername);
    }

    @GetMapping("/bot-token")
    public ResponseEntity<String> getBotToken() {
        String botToken = telegramBotService.getBotToken();
        return ResponseEntity.ok("Токен бота: " + botToken);
    }

    @PostMapping("/init-bot")
    public ResponseEntity<String> initializeBot() {
        telegramBotService.init();
        return ResponseEntity.ok("Бот зарегистрирован и готов к работе.");
    }

    @PostMapping("/send-text-message")
    public ResponseEntity<String> sendTextMessage(@RequestParam("chatId") Long chatId, @RequestParam("text") String text) throws TelegramApiException {
        telegramBotService.sendTextMessageWithKeyboard(chatId, text, ReplyKeyboardMarkup.builder().build());
        return ResponseEntity.ok("Сообщение успешно отправлено.");
    }
    /*
        @GetMapping("/user/{userId}/stock")
        public ResponseEntity<String> getStockInfoCommand(@RequestParam("chatId") Long chatId, @PathVariable int userId) {
            JpaUser user = (JpaUser) userService.getUserById(userId);
            if (user == null || StringUtils.isEmpty(user.getStockTicker())) {
                return ResponseEntity.badRequest().body("Пользователь или тикер акции не найден");
            }
            StockDataEntity stockData = stockDataService.getStockData(user.getStockTicker());
            if (stockData == null) {
                return ResponseEntity.badRequest().body("Ошибка при получении данных об акции");
            }
            String stockInfo = String.format("Символ: %s, Цена: %s, Объем: %s", stockData.getSymbol(), stockData.getPrice(), stockData.getVolume());
            return ResponseEntity.ok(stockInfo);
        }*/
    ///user/123/stock?chatId=456&text=someText
    ///user/123/stock?text=someText

    @GetMapping("/user/{userId}/stock")
    public ResponseEntity<String> getStockInfoCommand(
            @RequestParam(value = "chatId", defaultValue = "0") Long chatId,
            @RequestParam("text") String text,
            @PathVariable int userId) {
        // Создаем локальный логгер внутри метода
        Logger logger = LoggerFactory.getLogger(getClass());

        JpaUser user = (JpaUser) userService.getUserById(userId);
        if (user == null || StringUtils.isBlank(user.getStockTicker())) {
            return ResponseEntity.badRequest().body("Пользователь или тикер акции не найден");
        }

        try {
            // Используем метод для извлечения тикера из текста сообщения
            String stockTicker = getStockTickerFromMessage(text);

            // Дополнительная проверка, если тикер не удалось извлечь
            if (StringUtils.isBlank(stockTicker)) {
                return ResponseEntity.badRequest().body("Тикер акции не найден в сообщении");
            }

            StockDataEntity stockData = stockDataService.getStockData(stockTicker);

            if (stockData == null) {
                return ResponseEntity.badRequest().body("Ошибка при получении данных об акции");
            }

            String stockInfo = String.format("Символ: %s, Цена: %.2f, Объем: %s",
                    stockData.getSymbol(), stockData.getPrice(), stockData.getVolume());

            return ResponseEntity.ok(stockInfo);

        } catch (Exception e) {
            logger.error("Ошибка при обработке запроса от пользователя {}. Текст сообщения: {}", userId, text, e);
            e.printStackTrace(); // Обработка логов
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера");
        }
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
}


