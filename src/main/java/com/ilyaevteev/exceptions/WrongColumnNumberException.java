package com.ilyaevteev.exceptions;

public class WrongColumnNumberException extends NumberFormatException{
    public WrongColumnNumberException(String message) {
        super(message);
    }
}
