package com.soundmix.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReadCommand extends DomainObject implements Command {
    private String username;

    public ReadCommand(String username) {
        this.username = username;
    }

    @Override
    public List<TimedMessage> execute() {
        User user = UserFactory.get(username);
        if (user == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(user.timeline().stream().sorted().collect(Collectors.toList()));
    }
}
