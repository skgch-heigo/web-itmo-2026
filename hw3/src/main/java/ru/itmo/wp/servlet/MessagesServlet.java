package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MessagesServlet extends HttpServlet {

    private static class Message {
        String user;
        String text;
        int id;

        Message(String _text, String _user, int id_) {
            text = _text;
            user = _user;
            id = id_;
        }
    }

    Map<Integer, Message> messages = new ConcurrentHashMap<Integer, Message>();
    AtomicInteger messageCounter = new AtomicInteger(0);
    private final Gson gson = new Gson();

    private void writeJsonResponse(Object objectToConvert, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String json = gson.toJson(objectToConvert);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private void doAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("user");
        if (user != null) {
            request.getSession().setAttribute("user", user);
        }
        Object userNow = request.getSession().getAttribute("user");
        if (userNow == null) {
            writeJsonResponse("", response);
        } else {
            writeJsonResponse(userNow, response);
        }
    }

    private void doFindAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Message> messageList = new ArrayList<>(messages.values());
        messageList.sort(Comparator.comparingInt(m -> m.id));
        writeJsonResponse(messageList, response);
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");
        String text = request.getParameter("text");
        if (user == null || text == null || text.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int messageId = messageCounter.getAndAdd(1);
        messages.put(messageId, new Message(text, user.toString(), messageId));
        writeJsonResponse("OK", response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getPathInfo();
        switch (uri) {
            case "/auth":
                doAuth(request, response);
                break;
            case "/findAll":
                doFindAll(request, response);
                break;
            case "/add":
                doAdd(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

}
