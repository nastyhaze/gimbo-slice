package com.nastyHaze.gimboslice.exception;

public class AppendCommandException extends RuntimeException {

    public AppendCommandException() {
    }

    public AppendCommandException(String message) {
        super(message);
    }
}
