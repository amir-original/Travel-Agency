package com.dev.exchange_rate.repository;

public class DuplicatePrimaryKeyException extends RuntimeException {
    public DuplicatePrimaryKeyException() {
    }

    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
