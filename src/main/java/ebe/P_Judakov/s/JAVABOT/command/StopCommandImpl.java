package ebe.P_Judakov.s.JAVABOT.command;

import ebe.P_Judakov.s.JAVABOT.command.interfaces.StopCommand;
import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.Message;
import ebe.P_Judakov.s.JAVABOT.service.jpa.TelegramBotService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class StopCommandImpl implements StopCommand {

    private Long chatId;
    private TelegramBotService bot;

    public StopCommandImpl(Long chatId, TelegramBotService bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    // Реализация логики для команды "stop"
    @Override
    public void executeStop() throws TelegramApiException {
        // Сообщение пользователю о завершении работы бота
        sendTextMessage(chatId, "Работа бота завершена.");
    }

    private void sendTextMessage(Long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        Message sentMessage = (Message) bot.execute(message);
        // Обработка успешной отправки сообщения (sentMessage содержит информацию о сообщении)
        System.out.println("Сообщение успешно отправлено: " + sentMessage);
    }



}
