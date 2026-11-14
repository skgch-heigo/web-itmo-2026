package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})
public class IndexPage extends Page {
    private final UserService userService = new UserService();
    private final ArticleService articleService = new ArticleService();

    @Json
    protected void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
        List<User> users = userService.findAll();
        HashMap<String, String> userLogins = new HashMap<>();
        for (User user : users) {
            userLogins.put(Long.toString(user.getId()), user.getLogin());
        }
        view.put("userLogins", userLogins);
    }
}
