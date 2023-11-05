package ebe.P_Judakov.s.JAVABOT.command;

import ebe.P_Judakov.s.JAVABOT.command.interfaces.StartCommand;

import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommandImpl implements StartCommand {

        private Long chatId;
        private TelegramBotService bot;

   public StartCommandImpl(Long chatId, TelegramBotService bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    @Override
        public void executeStart() {
            // Реализация логики для команды "start"
            sendWelcomeMessage();
        }

    private void sendWelcomeMessage() {
        // Приветственное сообщение пользователю
        String welcomeText = "Добро пожаловать! Я ваш телеграм-бот.";
        welcomeText += " Начните взаимодействие с командой /help.";
        // Метод отправки сообщения
        sendTextMessage(chatId, welcomeText);
    }

        private void sendTextMessage(Long chatId, String text) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(text);

            bot.execute(message); // Используйте бот для отправки сообщения
        }
    }
