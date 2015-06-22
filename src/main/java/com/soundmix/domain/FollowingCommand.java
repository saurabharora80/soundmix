package com.soundmix.domain;

import java.util.Collections;
import java.util.List;

public class FollowingCommand extends DomainObject implements Command {
    private final String userFollowing;
    private final String userBeingFollowed;

    public FollowingCommand(String userFollowing, String userBeingFollowed) {
        this.userFollowing = userFollowing;
        this.userBeingFollowed = userBeingFollowed;
    }

    @Override
    public List<TimedMessage> execute() {
        UserFactory.get(userBeingFollowed).isFollowedBy(userFollowing);
        UserFactory.get(userFollowing).isFollowing(userBeingFollowed);
        return Collections.emptyList();
    }
}
