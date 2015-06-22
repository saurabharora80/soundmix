package com.soundmix.domain;

import java.util.List;

public interface Command {
    List<TimedMessage> execute();
}
