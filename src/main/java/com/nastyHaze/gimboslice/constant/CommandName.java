package com.nastyHaze.gimboslice.constant;

/**
 *  Used to track Command names. Used to specify extra logic needed for individual commands.
 */
public enum CommandName {

    INFO("Info"),

    COMMANDS("Commands"),

    GROUP_GOAL("Group Goal"),

    CLUELESS("Clueless"),

    BARROWS("Barrows"),

    GEAR_REQUIREMENTS("Gear Requirements"),

    CHAMBERS_REQ("Chambers of Xeric Requirements"),

    DROP("Drop");

    private final String desc;

    CommandName(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
