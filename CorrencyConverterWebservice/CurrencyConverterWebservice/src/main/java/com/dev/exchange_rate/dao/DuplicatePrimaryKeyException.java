package com.dev.exchange_rate.dao;

public class DuplicatePrimaryKeyException extends RuntimeException {
    public DuplicatePrimaryKeyException() {
    }

    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
