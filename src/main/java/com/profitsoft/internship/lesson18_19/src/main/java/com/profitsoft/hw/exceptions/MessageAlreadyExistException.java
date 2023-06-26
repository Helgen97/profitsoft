package com.profitsoft.hw.exceptions;

public class MessageAlreadyExistException extends RuntimeException{

    public MessageAlreadyExistException(String message) {
        super(message);
    }
}
