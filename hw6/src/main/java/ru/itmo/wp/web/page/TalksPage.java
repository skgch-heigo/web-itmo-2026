package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        List<User> users = userService.findAll();
        HashMap<String, String> userLogins = new HashMap<>();
        for (User user : users) {
            userLogins.put(Long.toString(user.getId()), user.getLogin());
        }
        view.put("userLogins", userLogins);
        view.put("talks", talkService.findAllByUserId(((User)request.getSession().getAttribute("user")).getId()));
    }

    protected void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Talk talk = new Talk();
        talk.setText(request.getParameter("text"));
        talk.setSourceUserId(((User)request.getSession().getAttribute("user")).getId());
        talk.setTargetUserId(Long.parseLong(request.getParameter("targetUserId")));
        talkService.validateTalk(talk);
        talkService.save(talk);
        throw new RedirectException("/talks");
    }
}
