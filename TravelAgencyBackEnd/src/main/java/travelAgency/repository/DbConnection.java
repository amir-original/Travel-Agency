package travelAgency.repository;

import java.sql.Connection;

public interface DbConnection {

    Connection getConnection();
    void close();
}
