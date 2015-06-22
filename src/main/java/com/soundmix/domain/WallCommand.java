package com.soundmix.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WallCommand extends DomainObject implements Command {
    private String username;

    public WallCommand(String username) {
        this.username = username;
    }

    @Override
    public List<TimedMessage> execute() {
        User user = UserFactory.get(username);
        Stream<User> followedUsers = user.getFollowing().stream().map(UserFactory::get);

        List<TimedMessage> allTimedMessages = followedUsers.map(User::timeline)
                                                            .flatMap(Collection::stream)
                                                            .collect(Collectors.toList());

        allTimedMessages.addAll(user.timeline());
        Collections.sort(allTimedMessages);
        return Collections.unmodifiableList(allTimedMessages);

    }
}
