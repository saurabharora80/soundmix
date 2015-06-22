package com.soundmix.domain;

import java.util.Collections;
import java.util.List;

public class PostCommand extends DomainObject implements Command {
    private final String username;
    private final String message;

    public PostCommand(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public List<TimedMessage> execute() {
        UserFactory.get(username).post(message);
        return Collections.emptyList();
    }

}
