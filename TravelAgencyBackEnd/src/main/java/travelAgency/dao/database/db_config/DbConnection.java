package travelAgency.dao.database.db_config;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();
    void truncate(String tableName);
}
