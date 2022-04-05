package com.nastyHaze.gimboslice.constant;

public enum Operator {

    QUERY("?"),

    UPDATE("!"),

    APPEND("+"),

    REMOVE("-");

    private String opCode;

    Operator(String opCode) {
        this.opCode = opCode;
    }

    public String opCode() {
        return opCode;
    }
}
