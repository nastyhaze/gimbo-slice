package com.nastyHaze.gimboslice.exception;


/**
 *  Custom Exception for interruptions in command execution.
 */
public class CommandExecutionException extends RuntimeException {

    public CommandExecutionException() {
    }

    public CommandExecutionException(String message) {
        super(message);
    }
}
