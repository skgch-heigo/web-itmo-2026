package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Json
    private void setAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id = Long.parseLong(request.getParameter("id"));
        boolean admin = request.getParameter("admin").equals("true");
        userService.validateAdmin(((User)request.getSession().getAttribute("user")).getId());
        if (userService.find(id) == null) {
            throw new ValidationException("No such user");
        }
        userService.setAdmin(id, admin);
    }
}
