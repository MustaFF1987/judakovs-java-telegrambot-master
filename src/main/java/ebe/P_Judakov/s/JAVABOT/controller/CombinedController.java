package ebe.P_Judakov.s.JAVABOT.controller;

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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    @GetMapping("/user/{userId}/stock")
    public ResponseEntity<String> getStockInfoCommand(@RequestParam("chatId") Long chatId, @PathVariable int userId) {
        JpaUser user = (JpaUser) userService.getUserById(userId);

        if (user == null || StringUtils.isEmpty(user.getStockTicker())) {
            return ResponseEntity.badRequest().body("User or stock ticker not found");
        }

        StockDataEntity stockData = stockDataService.getStockData(user.getStockTicker());

        if (stockData == null) {
            return ResponseEntity.badRequest().body("Error fetching stock data");
        }

        String stockInfo = String.format("Symbol: %s, Price: %s, Volume: %s", stockData.getSymbol(), stockData.getPrice(), stockData.getVolume());

        return ResponseEntity.ok(stockInfo);
    }
}
