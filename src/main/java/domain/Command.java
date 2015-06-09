package domain;

import java.util.Stack;

public interface Command {
    Stack<TimedMessage> execute();
}
