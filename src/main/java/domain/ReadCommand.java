package domain;

import java.util.Stack;

public class ReadCommand implements Command {
    private String username;

    public ReadCommand(String username) {
        this.username = username;
    }

    @Override
    public Stack<TimedMessage> execute() {
        User user = UserFactory.get(username);
        if (user == null) {
            return new Stack<>();
        }
        return user.timeline();
    }
}
