package com.soundmix.domain;

import java.util.List;
import java.util.Stack;

public class User {

    private final String name;
    private final Stack<TimedMessage> timeline;
    private final List<String> following;
    private final List<String> followers;

    public User(String name) {
        this.name = name;
        this.timeline = new Stack<>();
        this.following = new Stack<>();
        this.followers = new Stack<>();
    }

    public void post(String message) {
        timeline.push(new TimedMessage(message));
    }

    public Stack<TimedMessage> timeline() {
        return timeline;
    }

    public void isFollowing(String userBeingFollowed) {
        this.following.add(userBeingFollowed);
    }

    public void isFollowedBy(String userFollowing) {
        this.followers.add(userFollowing);
    }

    public List<String> getFollowing() {
        return following;
    }
}
