package ebe.P_Judakov.s.JAVABOT.controller;

import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.JpaUser;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.StockDataEntity;
import ebe.P_Judakov.s.JAVABOT.service.jpa.JpaUserService;
import ebe.P_Judakov.s.JAVABOT.service.jpa.StockDataService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
public class UserController {

    private final JpaUserService userService;
    private final StockDataService stockDataService;

    public UserController(JpaUserService userService, StockDataService stockDataService) {
        this.userService = userService;
        this.stockDataService = stockDataService;
    }

    @GetMapping("/user/{userId}/stock")
    public ResponseEntity<String> getStockInfo(@PathVariable int userId) {
        JpaUser user = (JpaUser) userService.getUserById(userId);

        if (user == null || StringUtils.isEmpty(user.getStockTicker())) {
            return ResponseEntity.badRequest().body("Пользователь или тикер акции не найден");
        }

        StockDataEntity stockData = stockDataService.getStockData(user.getStockTicker());

        if (stockData == null) {
            return ResponseEntity.badRequest().body("Ошибка при получении данных об акции.");
        }

        // Формируем строку с данными акции
        String stockInfo = String.format("Символ: %s, Цена: %s, Объем: %s", stockData.getSymbol(), stockData.getPrice(), stockData.getVolume());

        return ResponseEntity.ok(stockInfo);
    }
}