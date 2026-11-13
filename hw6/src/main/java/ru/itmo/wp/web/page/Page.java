package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    void before(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
        this.request = request;
    }
    void after(HttpServletRequest request, Map<String, Object> view) {}
    protected void action(HttpServletRequest request, Map<String, Object> view) {}
    public void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }
}
