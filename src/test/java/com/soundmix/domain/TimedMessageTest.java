package com.soundmix.domain;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class TimedMessageTest {

    @Test
    public void messageMustBeDisplayedWithCorrectTimeDelay() throws Exception {
        TestTimeUtil.setCurrentTimeTo("15-June-2015 00:00:30");
        TimedMessage messageOne = new TimedMessage("message one");

        TestTimeUtil.setCurrentTimeTo("15-June-2015 00:01:35");
        TimedMessage messageTwo = new TimedMessage("message two");

        TestTimeUtil.setCurrentTimeTo("15-June-2015 00:02:35");
        TimedMessage messageThree = new TimedMessage("message three");

        TestTimeUtil.setCurrentTimeTo("15-June-2015 00:02:40");
        TimedMessage messageFour = new TimedMessage("message four");

        assertThat(messageFour.timelineMessage()).isEqualTo("message four (now)");
        assertThat(messageThree.timelineMessage()).isEqualTo("message three (5 seconds ago)");
        assertThat(messageTwo.timelineMessage()).isEqualTo("message two (1 minute ago)");
        assertThat(messageOne.timelineMessage()).isEqualTo("message one (2 minutes ago)");

        TestTimeUtil.resetTime();

    }


}