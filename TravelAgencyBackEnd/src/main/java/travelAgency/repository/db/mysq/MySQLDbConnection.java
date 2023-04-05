package travelAgency.repository.db.mysq;

import travelAgency.helper.PropertiesReader;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDbConnection implements DbConnection {
    private String host;
    private String user;
    private String pass;
    private Connection connection;
    private String configFileName = "db-config";

    public MySQLDbConnection() {
        loadConfig();
    }

    public MySQLDbConnection(String host,String user,String pass) {
        this.host = host;
        this.user = user;
        this.pass = pass;
    }

    private void loadConfig() {
        final PropertiesReader propertiesReader = new PropertiesReader(configFileName);
        host = propertiesReader.getProperty("host");
        user = propertiesReader.getProperty("username");
        pass = propertiesReader.getProperty("password");
    }

    @Override
    public Connection getConnection() {
        try {
            return connection = DriverManager.getConnection(host, user, pass);
        } catch (SQLException e) {
            throw new DatabaseConnectionException();
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
