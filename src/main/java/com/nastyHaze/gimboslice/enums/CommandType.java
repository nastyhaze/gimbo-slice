package com.nastyHaze.gimboslice.enums;

/**
 *  ENUM for the 'type' column of the 'command' table.
 */
public enum CommandType {

    MESSAGE_COMMAND(1L, "Message Command");

    private final long id;
    private final String desc;

    CommandType(long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public long id() {
        return id;
    }

    public String desc() {
        return desc;
    }
}
