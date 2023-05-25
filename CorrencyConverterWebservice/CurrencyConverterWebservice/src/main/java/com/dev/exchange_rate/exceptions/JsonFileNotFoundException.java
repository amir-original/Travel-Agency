package com.dev.exchange_rate.exceptions;

public class JsonFileNotFoundException extends RuntimeException{
    public JsonFileNotFoundException(String message) {
        super(message);
    }

    public JsonFileNotFoundException() {
    }
}
