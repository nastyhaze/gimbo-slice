package com.nastyHaze.gimboslice.constant;

/**
 *  Operators that are used to execute Commands & specify the CommandService required to handle their execution.
 */
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
