package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage extends Page {
    private final EventService eventService = new EventService();

    @Override
    void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            Event event = new Event();
            event.setUserId(user.getId());
            event.setType(Event.EventType.LOGOUT);
            eventService.addEvent(event);

            request.getSession().removeAttribute("user");
            setMessage("Good bye. Hope to see you soon!");
        }

        throw new RedirectException("/index");
    }
}
