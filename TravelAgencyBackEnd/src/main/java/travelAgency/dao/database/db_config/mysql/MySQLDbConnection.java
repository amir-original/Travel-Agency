package travelAgency.dao.database.db_config.mysql;

import travelAgency.helper.PropertiesReader;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDbConnection implements DbConnection {
    private String host;
    private String user;
    private String pass;
    private Connection connection;

    public MySQLDbConnection() {
        PropertiesReader propertiesReader = new PropertiesReader("db-config");
        loadConfig(propertiesReader);
    }

    public MySQLDbConnection(String host, String user, String pass) {
        this.host = host;
        this.user = user;
        this.pass = pass;
    }

    private void loadConfig(PropertiesReader propertiesReader) {
        host = propertiesReader.getProperty("host");
        user = propertiesReader.getProperty("username");
        pass = propertiesReader.getProperty("password");
    }

    @Override
    public Connection getConnection() {
        try {
            return connection = DriverManager.getConnection(host, user, pass);
        } catch (SQLException e) {
            throw new CouldNotConnectToDatabase();
        }
    }

    @Override
    public void truncate(String tableName) {
        try (final PreparedStatement truncate = connection.prepareStatement("TRUNCATE TABLE " + tableName)) {
            truncate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
