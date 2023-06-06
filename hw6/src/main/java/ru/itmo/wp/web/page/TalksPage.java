package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();
    private final UserService userService = new UserService();

    @Override
    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("Not an authorized user");
            throw new RedirectException("/index");
        }
        view.put("talks", talkService.findByUser(getUser()));
        view.put("users", userService.findAll());
    }

    void addTalk(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(userService.findByLogin(request.getParameter("targetUserLogin")).getId());
        talk.setText(request.getParameter("text"));
        talkService.validateTalk(getUser(), talk);
        talkService.addTalk(talk);

        setMessage("Message sent successfully");
        throw new RedirectException("/talks");
    }
}
