package ebe.P_Judakov.s.JAVABOT.command;

import ebe.P_Judakov.s.JAVABOT.command.interfaces.StopCommand;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.service.jpa.MyTelegramBotService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class StopCommandImpl implements StopCommand {

    private Long chatId;
    private MyTelegramBotService bot;

    public StopCommandImpl(Long chatId, MyTelegramBotService bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    // Реализация логики для команды "stop"
    @Override
    public void executeStop() {
        // Сообщение пользователю о завершении работы бота
        sendTextMessage(chatId, "Работа бота завершена.");
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            Message sentMessage = (Message) bot.execute(message);
            // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
            System.out.println("Сообщение успешно отправлено: " + sentMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Обработка ошибок отправки сообщения
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }



}
