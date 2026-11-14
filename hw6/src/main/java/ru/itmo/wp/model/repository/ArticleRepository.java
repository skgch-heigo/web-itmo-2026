package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);

    List<Article> findAll(boolean hideHidden);
    List<Article> findAllByUserId(long id);

    void save(Article article);

    void setHidden(long id, boolean newHidden);
}
