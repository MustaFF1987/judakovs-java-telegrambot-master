package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.Article;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.ApiClientInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Пример реализации apiClient

@Service
public class ApiClientService implements ApiClientInterface {
    // Ваша логика для получения новых статей с вашего API
    @Override
    public List<Article> getNewArticles(int lastArticleId) {
        // Ваш код для получения новых статей, начиная с lastArticleId
        // ...

        // Пример создания списка новых статей (замените на вашу логику)
        List<Article> newArticles = new ArrayList<>();
        newArticles.add(new Article(1, "Новая статья 1"));
        newArticles.add(new Article(2, "Новая статья 2"));
        newArticles.add(new Article(3, "Новая статья 3"));

        return newArticles;
    }
}
