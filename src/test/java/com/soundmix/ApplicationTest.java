package com.soundmix;

import com.soundmix.domain.CommandFactory;
import com.soundmix.domain.TestTimeUtil;
import com.soundmix.domain.TimedMessage;
import com.soundmix.domain.UserFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

public class ApplicationTest {

    @Before
    public void resetUserFactory() {
        UserFactory.reset();
    }

    @Test
    public void bobCanPostAMessage() throws Exception {
        user("Bob").posts("We lost the game!").at("15-June-2015 00:00:30");
        assertUser("Bob").posted("We lost the game!").at("15-June-2015 00:00:30");
    }

    @Test
    public void bobCanPostMultipleMessagesWithTimeLapse() throws Exception {
        user("Bob").posts("We lost the game!").at("15-June-2015 00:00:30");
        user("Bob").posts("Good game thou.").at("15-June-2015 00:05:30");

        assertUser("Bob").timeline().hasMessagesInThisOrder("Good game thou. @(15-June-2015 00:05:30)", "We lost the game! @(15-June-2015 00:00:30)");
    }

    @Test
    public void bobCanPostMultipleMessages() throws Exception {
        user("Bob").posts("We lost the game!").at("15-June-2015 00:00:30");
        user("Bob").posts("Good game thou.").at("15-June-2015 00:05:30");

        assertUser("Bob").timeline().hasMessagesInThisOrder("Good game thou. @(15-June-2015 00:05:30)", "We lost the game! @(15-June-2015 00:00:30)");
    }

    @Test
    public void BobAndAliceCanPostMessagesToTheirTimelines() throws Exception {
        user("Bob").posts("We lost the game!").at("15-June-2015 00:00:30");
        user("Bob").posts("Good game thou.").at("15-June-2015 00:05:30");

        user("Alice").posts("I love the weather today.").at("15-June-2015 00:10:30");

        user("Bob").timeline().hasMessagesInThisOrder("Good game thou. @(15-June-2015 00:05:30)", "We lost the game! @(15-June-2015 00:00:30)");
        user("Alice").timeline().hasMessagesInThisOrder("I love the weather today. @(15-June-2015 00:10:30)");
    }

    @Test
    public void charlieCanFollowAlice() throws Exception {
        user("Alice").posts("I love the weather today.").at("15-June-2015 12:00:30");

        user("Charlie").posts("I'm in New York today! Anyone want to have a coffee?").at("15-June-2015 12:01:30");

        user("Charlie").follows("Alice");

        assertUser("Charlie").wall()
                .hasMessagesInThisOrder("I'm in New York today! Anyone want to have a coffee? @(15-June-2015 12:01:30)",
                        "I love the weather today. @(15-June-2015 12:00:30)");
    }

    @Test
    public void charlieFollowsAliceAndBob() throws Exception {
        user("Bob").posts("We lost the game!").at("15-June-2015 00:00:30");
        user("Bob").posts("Good game thou.").at("15-June-2015 00:01:30");

        user("Alice").posts("I love the weather today.").at("15-June-2015 00:05:30");

        user("Charlie").posts("I'm in New York today! Anyone want to have a coffee?").at("15-June-2015 00:05:35");

        user("Charlie").follows("Alice");
        user("Charlie").follows("Bob");

        assertUser("Charlie").wall()
                .hasMessagesInThisOrder("I'm in New York today! Anyone want to have a coffee? @(15-June-2015 00:05:35)",
                        "I love the weather today. @(15-June-2015 00:05:30)", "Good game thou. @(15-June-2015 00:01:30)",
                        "We lost the game! @(15-June-2015 00:00:30)");
    }

    private static TestWrapper user(String username) {
        return new TestWrapper(username);
    }

    private static TestWrapper assertUser(String username) {
        return new TestWrapper(username);
    }

    private static class PostWrapper {
        private String username;
        private String message;

        public PostWrapper(String username, String message) {
            this.username = username;
            this.message = message;
        }
        public void at(String dateAsString) throws ParseException {
            TestTimeUtil.setCurrentTimeTo(dateAsString);
            CommandFactory.build(String.format("%s -> %s", username, message)).execute();
            TestTimeUtil.resetTime();
        }
    }

    private static class ReadWrapper {

        private String username;
        private String message;

        public ReadWrapper(String username, String message) {
            this.username = username;
            this.message = message;
        }

        public void at(String dateAsString) throws ParseException {
            new MessageAssertion(CommandFactory.build(username).execute())
                    .hasMessagesInThisOrder(String.format("%s @(%s)", message, dateAsString));
        }
    }

    private static class TestWrapper {

        private String username;

        public TestWrapper(String username) {
            this.username = username;
        }

        public PostWrapper posts(String message) {
            return new PostWrapper(username, message);
        }

        public ReadWrapper posted(String message) {
            return new ReadWrapper(username, message);
        }

        public MessageAssertion timeline() {
            return new MessageAssertion(CommandFactory.build(username).execute());
        }

        public void follows(String anotherUsername) {
            CommandFactory.build(String.format("%s follows %s", username, anotherUsername)).execute();
        }

        public MessageAssertion wall() {
            return new MessageAssertion(CommandFactory.build(String.format("%s wall", username)).execute());
        }

    }

    private static final class MessageAssertion {
        private List<TimedMessage> actualMessages;

        public MessageAssertion(List<TimedMessage> messages) {
            this.actualMessages = messages;
        }

        public void hasMessagesInThisOrder(String... expectedMessages) {
            assertThat(actualMessages.size()).isEqualTo(expectedMessages.length);

            List<TimedMessage> expectedTimedMessages = Arrays.asList(expectedMessages).stream()
                    .map(ExpectedTimedMessage::new)
                    .collect(Collectors.toList());

            assertThat(actualMessages).containsSequence(expectedTimedMessages.toArray(new TimedMessage[]{}));

        }
    }

    private static class ExpectedTimedMessage extends TimedMessage {
        public ExpectedTimedMessage(String message, DateTime date) {
            super(message, date);
        }

        public ExpectedTimedMessage(String messageWithTime) {
            this(messageWithTime.split("@\\(")[0].trim(),
                    TestTimeUtil.toDateTime(messageWithTime.split("@\\(")[1].replace("\\)", "")));
        }
    }
}

