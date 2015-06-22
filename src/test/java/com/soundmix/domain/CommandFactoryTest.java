package com.soundmix.domain;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CommandFactoryTest {
    @Test
    public void constructPostCommand() {
        assertThat(CommandFactory.build("Bob -> message one")).isEqualTo(new PostCommand("Bob", "message one"));
        assertThat(CommandFactory.build("Bob -> message one T&*&^^")).isEqualTo(new PostCommand("Bob", "message one T&*&^^"));
    }

    @Test
    public void constructFollowCommand() {
        assertThat(CommandFactory.build("Bob follows Alice")).isEqualTo(new FollowingCommand("Bob", "Alice"));
        assertThat(CommandFactory.build("Charlie follows Bob")).isEqualTo(new FollowingCommand("Charlie", "Bob"));
    }

    @Test
    public void constructWallCommand() {
        assertThat(CommandFactory.build("Bob wall")).isEqualTo(new WallCommand("Bob"));
        assertThat(CommandFactory.build("Charlie wall")).isEqualTo(new WallCommand("Charlie"));
    }

    @Test
    public void constructReadCommand() {
        assertThat(CommandFactory.build("Bob")).isEqualTo(new ReadCommand("Bob"));
        assertThat(CommandFactory.build("Charlie")).isEqualTo(new ReadCommand("Charlie"));
    }
}