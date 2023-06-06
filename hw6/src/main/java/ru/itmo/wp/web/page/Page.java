package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    private static final UserService userService = new UserService();
    private static HttpServletRequest savedRequest;

    void before(HttpServletRequest request, Map<String, Object> view) {
        savedRequest = request;

        view.put("userCount", userService.findCount());

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }

        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    void setMessage(String message) {
        savedRequest.getSession().setAttribute("message", message);
    }

    void setUser(User user) {
        savedRequest.getSession().setAttribute("user", user);
    }

    User getUser() {
        return (User) savedRequest.getSession().getAttribute("user");
    }

    void after(HttpServletRequest request, Map<String, Object> view) {

    }

    void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
