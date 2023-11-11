package ebe.P_Judakov.s.JAVABOT.service.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.StockDataEntity;
import org.springframework.stereotype.Service;


@Service
public class StockDataService {

    private static final String API_BASE_URL = "https://alpha-vantage.p.rapidapi.com/query";
    private static final String API_FUNCTION_GLOBAL_QUOTE = "GLOBAL_QUOTE";

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public StockDataService() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public StockDataEntity getStockData(String symbol) {
        try {
            // Формирование URL-запроса
            String apiUrl = String.format("%s?function=%s&symbol=%s", API_BASE_URL, API_FUNCTION_GLOBAL_QUOTE, symbol);

            // Создание запроса
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .get()
                    .addHeader("X-RapidAPI-Key", "280933c57emsh1239af775f237abp113ad4jsnd2923af4a4b4")
                    .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .build();

            // Отправка запроса и получение ответа
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                // Обработка ошибок, например, вывод логов или выброс исключения
                System.out.println("Ошибка: " + response.code() + " - " + response.message());
                return null;
            }

            // Обработка ответа
            String responseBody = response.body().string();
            StockDataEntity stockData = objectMapper.readValue(responseBody, StockDataEntity.class);

            return stockData;

        } catch (Exception e) {
            // Обработка исключений, например, вывод логов или выброс исключения
            e.printStackTrace();
            return null;
        }
    }
}