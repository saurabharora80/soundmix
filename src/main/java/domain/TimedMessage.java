package domain;

import java.util.Date;

public class TimedMessage {

    private final Date createdOn;
    private String message;

    public TimedMessage(String message) {
        this.message = message;
        this.createdOn = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TimedMessage)) {
            return false;
        }
        return this.message.equals(((TimedMessage)obj).message);
    }
}
