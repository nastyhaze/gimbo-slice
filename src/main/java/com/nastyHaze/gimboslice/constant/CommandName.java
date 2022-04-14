package com.nastyHaze.gimboslice.constant;

/**
 *  Used to track Command names. Used to specify extra logic needed for individual commands.
 */
public enum CommandName {

    INFO("Info"),

    COMMANDS("Commands"),

    GROUP_GOAL("Group Goal"),

    CLUELESS("Clueless");

    private String desc;

    CommandName(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
