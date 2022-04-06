package com.nastyHaze.gimboslice.constant;

public enum CommandName {

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
