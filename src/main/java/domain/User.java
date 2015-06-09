package domain;

import java.util.Stack;

public class User {

    private final String name;
    private final Stack<TimedMessage> timeline;

    public User(String name) {
        this.name = name;
        this.timeline = new Stack<>();
    }

    public void post(String message) {
        timeline.push(new TimedMessage(message));
    }

    public Stack<TimedMessage> timeline() {
        return timeline;
    }
}
