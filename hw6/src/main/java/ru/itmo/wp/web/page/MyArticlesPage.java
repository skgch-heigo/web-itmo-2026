package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        long id = ((User)request.getSession().getAttribute("user")).getId();
        view.put("articles", articleService.findAllByUserId(id));
    }

    @Json
    private void setHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id = Long.parseLong(request.getParameter("id"));
        boolean hidden = request.getParameter("hidden").equals("true");
        if (articleService.find(id).getUserId() != ((User)request.getSession().getAttribute("user")).getId()) {
            throw new ValidationException("You can change status only of your own articles");
        }
        articleService.setHidden(id, hidden);
    }
}
