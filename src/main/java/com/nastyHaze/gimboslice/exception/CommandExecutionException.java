package com.nastyHaze.gimboslice.exception;

public class CommandExecutionException extends RuntimeException {

    public CommandExecutionException() {
    }

    public CommandExecutionException(String message) {
        super(message);
    }
}
