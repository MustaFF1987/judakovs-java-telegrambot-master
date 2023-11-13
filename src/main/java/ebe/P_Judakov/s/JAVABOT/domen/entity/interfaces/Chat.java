package ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;

public interface Chat {

    int getId();
    int getChatId();
    String getType();

    void addUser(JpaUser user);
}
