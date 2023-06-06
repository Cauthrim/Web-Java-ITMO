package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.DisplayTalk;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import java.util.List;

public class TalkService {
    private static final TalkRepositoryImpl talkRepository = new TalkRepositoryImpl();
    private static final UserService userService = new UserService();

    public List<DisplayTalk> findByUser(User user) {
        return talkRepository.findByUser(user);
    }

    public void addTalk(Talk talk) {
        talkRepository.save(talk);
    }

    public void validateTalk(User sessionUser, Talk talk) throws ValidationException {
        if (sessionUser.getId() != talk.getSourceUserId()) {
            throw new ValidationException("Sender and authorized user do not match");
        }
        if (userService.find(talk.getTargetUserId()) == null) {
            throw new ValidationException("Invalid message receiver");
        }
        if (talk.getText() == null || talk.getText().strip().isEmpty()) {
            throw new ValidationException("Empty messages are not allowed");
        }
        if (talk.getText().length() > 255) {
            throw new ValidationException("Text of the message cannot be longer than 255 characters");
        }
    }
}
