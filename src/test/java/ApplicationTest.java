import domain.CommandFactory;
import domain.TimedMessage;
import domain.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ApplicationTest {

    @Before
    public void resetUserFactory() {
        UserFactory.reset();
    }

    @Test
    public void postAMessage() {
        user("Bob").posts("We lost the game!").toHisTimeline();
        user("Bob").timeline().containsInOrder("We lost the game!");
    }

    @Test
    public void postMultipleMessages() {
        user("Bob").posts("We lost the game!", "Good game thou.").toHisTimeline();
        user("Bob").timeline().containsInOrder("Good game thou.", "We lost the game!");
    }

    @Test
    public void multipleUsersCanPostMessagesToTheirTimelines() {
        user("Bob").posts("We lost the game!", "Good game thou.").toHisTimeline();
        user("Alice").posts("I love the weather today").toHisTimeline();

        user("Bob").timeline().containsInOrder("Good game thou.", "We lost the game!");
        user("Alice").timeline().containsInOrder("I love the weather today");
    }

    private static TestWrapper user(String username) {
        return new TestWrapper(username);
    }

    static class TestWrapper {

        private String username;
        private List<String> messages;

        public TestWrapper(String username) {
            this.username = username;
        }

        public TestWrapper posts(String... messages) {
            this.messages = Arrays.asList(messages);
            return this;
        }

        public void toHisTimeline() {
            messages.stream().forEach(message -> CommandFactory.build(username, "->", message).execute());
        }

        public TimelineWrapper timeline() {
            return new TimelineWrapper(CommandFactory.build(username).execute());
        }

        static final class TimelineWrapper {
            private Stack<TimedMessage> messages;

            public TimelineWrapper(Stack<TimedMessage> messages) {
                this.messages = messages;
            }

            public void containsInOrder(String... expectedMessages) {
                Arrays.asList(expectedMessages).stream().forEach(expectedMessage ->
                        new TimedMessage(expectedMessage).equals(messages.pop()));
            }
        }
    }
}