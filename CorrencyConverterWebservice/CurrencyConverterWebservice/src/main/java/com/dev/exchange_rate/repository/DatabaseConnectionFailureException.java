package com.dev.exchange_rate.repository;

public class DatabaseConnectionFailureException extends RuntimeException {
    public DatabaseConnectionFailureException(String message) {
        super(message);
    }
}
