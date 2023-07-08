package travelAgency.infrastructure.db;

import java.sql.Connection;

public interface DbConnection {
    Connection currentConnection();
    void createTable(String tableSchema);
    void truncate(String tableName);
}
