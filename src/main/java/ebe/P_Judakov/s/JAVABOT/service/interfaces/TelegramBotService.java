package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import org.hibernate.sql.Update;

public interface TelegramBotService {

    // Обработка входящего обновления от Telegram API
    void onUpdateReceived(Update update);

    // Получение имени бота
    String getBotUsername();

    // Получение токена бота
    String getBotToken();

    void init();
}
