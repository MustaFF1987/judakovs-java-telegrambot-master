package ebe.P_Judakov.s.JAVABOT.service.interfaces;

import ebe.P_Judakov.s.JAVABOT.domen.entity.jpa.Article;

import java.util.List;

public interface ApiClientInterface {
        List<Article> getNewArticles(int lastArticleId);
    }