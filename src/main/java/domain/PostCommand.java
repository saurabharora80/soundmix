package domain;

import java.util.Stack;

public class PostCommand implements Command {
    private final String username;
    private final String message;

    public PostCommand(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public Stack<TimedMessage> execute() {
        User user = UserFactory.get(username);
        user.post(message);
        return user.timeline();
    }
}
