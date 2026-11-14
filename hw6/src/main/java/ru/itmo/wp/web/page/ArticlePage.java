package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticlePage extends Page {
    private final UserService userService = new UserService();
    private final ArticleService articleService = new ArticleService();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
    }


    protected void postArticle(HttpServletRequest request, Map<String, Object> view) throws  ValidationException {
        Article article = new Article();
        article.setText(request.getParameter("text"));
        article.setTitle(request.getParameter("title"));
        article.setUserId(((User)request.getSession().getAttribute("user")).getId());
        article.setHidden(false);
        articleService.validateArticle(article);
        articleService.save(article);
        throw new RedirectException("/index");
    }
}
