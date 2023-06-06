package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = userService.find(((User)request.getSession().getAttribute("user")).getId());
        view.put("user", user);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        view.put("user", userService.find(((User)request.getSession().getAttribute("user")).getId()));
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void switchAdmin(HttpServletRequest request, Map<String, Object> view) {
        User user = userService.find(Long.parseLong(request.getParameter("userId")));
        User sessionUser = userService.find(((User) request.getSession().getAttribute("user")).getId());
        boolean switchFlag = Boolean.parseBoolean(request.getParameter("switchFlag"));
        if (sessionUser != null && sessionUser.getAdmin()) {
            if (switchFlag != user.getAdmin()) {
                userService.switchAdmin(user, switchFlag);
            }
            request.getSession().setAttribute("user", userService.find(sessionUser.getId()));
            view.put("userId", user.getId());
            view.put("adminText", switchFlag ? "true" : "false");
            view.put("linkText", switchFlag ? "disable" : "enable");
        }
    }
}
