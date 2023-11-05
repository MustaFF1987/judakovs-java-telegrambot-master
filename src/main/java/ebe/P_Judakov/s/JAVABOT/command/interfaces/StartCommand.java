package ebe.P_Judakov.s.JAVABOT.command.interfaces;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface StartCommand {

    void executeStart() throws TelegramApiException;
}
