package ebe.P_Judakov.s.JAVABOT.controller;

import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@RestController
@RequestMapping("/telegram-bot")
public class TelegramBotController {

    /**
     * Сервис Сообщений.
     * Содержит бизнес-логику, относящуюся к телеграм боту.
     */

    @Autowired
    private TelegramBotService telegramBotService;

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


}