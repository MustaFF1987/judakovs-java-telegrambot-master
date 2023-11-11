package ebe.P_Judakov.s.JAVABOT.service.jpa;

import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.Article;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.ApiClientInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Реализация apiClient

@Service
public class ApiClientService implements ApiClientInterface {
    // Логика для получения новых статей с API
    @Override
    public List<Article> getNewArticles(int lastArticleId) {

        // Создание списка новых статей
        List<Article> newArticles = new ArrayList<>();
        newArticles.add(new Article(1, "Новая статья 1"));
        newArticles.add(new Article(2, "Новая статья 2"));
        newArticles.add(new Article(3, "Новая статья 3"));

        return newArticles;
    }
}
