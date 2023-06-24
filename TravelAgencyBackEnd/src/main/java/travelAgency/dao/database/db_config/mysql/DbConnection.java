package travelAgency.dao.database.db_config.mysql;

import java.sql.Connection;

public interface DbConnection {
    Connection currentConnection();
    void createTable(String tableSchema);
    void truncate(String tableName);
}
