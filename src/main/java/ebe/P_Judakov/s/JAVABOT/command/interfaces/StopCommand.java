package ebe.P_Judakov.s.JAVABOT.command.interfaces;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface StopCommand {

    void executeStop() throws TelegramApiException;
}
