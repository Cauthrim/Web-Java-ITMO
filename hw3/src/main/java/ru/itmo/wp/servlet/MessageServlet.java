package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

class Message {
    String user;
    String text;

    public Message(String us, String txt) {
        user = us;
        text = txt;
    }
}

public class MessageServlet extends HttpServlet {
    static List<Message> messages = new ArrayList<>();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String json = "";
        String uri = request.getRequestURI();
        if (uri.equals("/message/auth")) {
            String usr = Objects.requireNonNullElse(request.getParameter("user"), "").strip();
            if (!usr.isEmpty()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", usr);
                json = new Gson().toJson(usr);
            } else if (request.getSession().getAttribute("user") != null) {
                json = new Gson().toJson(request.getSession().getAttribute("user"));
            }
            response.setCharacterEncoding("UTF8");
            response.getWriter().print(json);
            response.getWriter().flush();
        }

        if (uri.equals("/message/findAll")) {
            if (request.getSession().getAttribute("user") == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            response.setCharacterEncoding("UTF8");
            json = new Gson().toJson(messages);
            response.getWriter().print(json);
            response.getWriter().flush();
        }

        if (uri.equals("/message/add")) {
            final String txt = Objects.requireNonNullElse(request.getParameter("text"), "").strip();
            String us = request.getSession().getAttribute("user").toString();
            if (txt.isEmpty() || us == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                response.setCharacterEncoding("UTF8");
                messages.add(new Message(us, txt));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
