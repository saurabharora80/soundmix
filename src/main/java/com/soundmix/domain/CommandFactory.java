package com.soundmix.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandFactory {

    public static Command build(String command) {
        Matcher matcher = Pattern.compile("(.*)\\s->\\s(.*)").matcher(command);
        if(matcher.matches()) {
            return new PostCommand(matcher.group(1), matcher.group(2));
        }
        matcher = Pattern.compile("(.*)\\sfollows\\s(.*)").matcher(command);
        if(matcher.matches()) {
            return new FollowingCommand(matcher.group(1), matcher.group(2));
        }
        matcher = Pattern.compile("(.*)\\swall").matcher(command);
        if(matcher.matches()) {
            return new WallCommand(matcher.group(1));
        }
        return new ReadCommand(command);
    }
}
