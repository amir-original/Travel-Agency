package com.dev.exchange_rate.dao;

import java.sql.SQLException;

public class DatabaseConnectionFailureException extends RuntimeException {
    public DatabaseConnectionFailureException(String message) {
        super(message);
    }
}
