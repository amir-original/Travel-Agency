package com.dev.exchange_rate.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionGateway implements DbConnection {
    private final ConnectionConfiguration configuration;
    private static Connection connection;

    public MySQLConnectionGateway(ConnectionConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Connection currentConnection() {
        try {
            if (connection != null) {
                return connection;
            }
            connection = DriverManager.getConnection(
                    configuration.url(),
                    configuration.username(),
                    configuration.password());
            return connection;
        } catch (SQLException e) {
            throw new DatabaseConnectionFailureException("Failed to establish a connection to the database");
        }
    }
}
