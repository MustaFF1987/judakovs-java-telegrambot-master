package ebe.P_Judakov.s.JAVABOT.controller;

import ebe.P_Judakov.s.JAVABOT.repository.interfaces.MessageRepository;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.MessageService;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    /**
     * Сервис Сообщений.
     * Содержит бизнес-логику, относящуюся к сообщениям.
     */
    @Autowired
    private MessageService messageService;

    private MessageRepository messageRepository;
}
