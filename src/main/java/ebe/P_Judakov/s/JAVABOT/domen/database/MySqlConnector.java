package ebe.P_Judakov.s.JAVABOT.domen.database;

import java.sql.Connection;
import java.sql.DriverManager;

import static ebe.P_Judakov.s.JAVABOT.constants.Constants.*;

public class MySqlConnector {

    public static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER_PATH);
            // jdbc:mysql://localhost:3306/`telegram_bot_10-170123-e-be`?user=root&password=123Unikorpa12
            String dbUrl = String.format("%s%s?user=%s&password=%s",
                    DB_ADDRESS, DB_NAME, DB_USER_NAME,DB_PASSWORD);
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e){
            throw new RuntimeException();
        }

    }
}