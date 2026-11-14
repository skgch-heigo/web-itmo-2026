package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserService userService = new UserService();

    public void validateTalk(Talk talk) throws ValidationException {
        if (userService.find(talk.getTargetUserId()) == null) {
            throw new ValidationException("Invalid target user");
        }
        if (Strings.isNullOrEmpty(talk.getText())) {
            throw new ValidationException("Message cannot be empty");
        }
        if (talk.getSourceUserId() == talk.getTargetUserId()) {
            throw new ValidationException("You cannot send a message to yourself");
        }
    }

    public Talk find(long id) {
        return talkRepository.find(id);
    }

    public List<Talk> findAllByUserId(long id) {
        return talkRepository.findAllByUserId(id);
    }

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

}
