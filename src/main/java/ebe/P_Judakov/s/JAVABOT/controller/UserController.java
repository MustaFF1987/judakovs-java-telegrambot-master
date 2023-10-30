package ebe.P_Judakov.s.JAVABOT.controller;

import ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces.User;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping("/user")
    public class UserController {


        /**
         * Сервис пользователей.
         * Содержит бизнес-логику, относящуюся к пользователям.
         */
        @Autowired
        private UserService userService;


        /**
         * Получение всех пользователей
         *
         * @return список всех покупателей, хранящихся в БД.
         */

        @GetMapping
        public List<User> getAll() {
            return userService.getAllUsers();
    }
}

