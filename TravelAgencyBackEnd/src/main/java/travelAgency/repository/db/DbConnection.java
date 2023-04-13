package travelAgency.repository.db;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();
    void truncate(String tableName);
}
