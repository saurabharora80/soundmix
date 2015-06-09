package domain;

public final class CommandFactory {
    private static Command build(Action action, String username, String message) {
        if (action == Action.POSTING) {
            return new PostCommand(username, message);
        }
        return new ReadCommand(username);
    }

    public static Command build(String... args) {
        String command = String.join(" ", args);
        if(command.contains("->")) {
            return build(Action.POSTING, args[0], args[2]);
        }
        return build(Action.READING, args[0], null);
    }

}
