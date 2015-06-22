package com.soundmix.domain;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;

public class TimedMessage extends DomainObject implements Comparable<TimedMessage> {

    private final DateTime createdOn;
    private String message;

    public TimedMessage(String message) {
        this(message, new DateTime());
    }

    protected TimedMessage(String message, DateTime createdOn) {
        this.message = message;
        this.createdOn = createdOn;
    }

    @Override
    public int compareTo(TimedMessage other) {
        return other.createdOn.compareTo(this.createdOn);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", message, new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(createdOn.toDate()));
    }

    public String timelineMessage() {
        return String.format("%s (%s)", message, new Delay(createdOn));
    }

    class Delay {
        private long timeDiffInSeconds;

        public Delay(DateTime createdOn) {
            timeDiffInSeconds = new DateTime().minus(createdOn.getMillis()).getMillis() / 1000;
        }

        @Override
        public String toString() {
            if (timeDiffInSeconds == 0) {
                return "now";
            }
            return String.format("%s ago", secondsOrMinutes(timeDiffInSeconds));
        }

        private String secondsOrMinutes(long timeDiffInSeconds) {
            if (timeDiffInSeconds > 60) {
                long timeDiffInMinutes = timeDiffInSeconds / 60;
                return String.format("%s %s", timeDiffInMinutes, timeDiffInMinutes == 1 ? "minute" : "minutes");
            }
            return String.format("%s %s", timeDiffInSeconds, timeDiffInSeconds == 1 ? "second" : "seconds");
        }
    }

}
