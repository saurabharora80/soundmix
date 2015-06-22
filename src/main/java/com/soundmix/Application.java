package com.soundmix;

import com.soundmix.domain.CommandFactory;
import com.soundmix.domain.TimedMessage;

import java.io.Console;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Console console = System.console();
        while (true) {
            List<TimedMessage> messages = CommandFactory.build(console.readLine()).execute();
            messages.forEach(message -> console.printf("%1$s \n", message.timelineMessage()));
        }
    }
}
