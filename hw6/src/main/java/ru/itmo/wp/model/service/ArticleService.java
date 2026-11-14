package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserService userService = new UserService();

    public void validateArticle(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Article title cannot be empty");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Article text cannot be empty");
        }
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAllByUserId(long id) {
        return articleRepository.findAllByUserId(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll(true);
    }

    public List<Article> findAllAndHidden() {
        return articleRepository.findAll(false);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void setHidden(long id, boolean newHidden) {
        articleRepository.setHidden(id, newHidden);
    }

}
