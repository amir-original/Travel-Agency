package travelAgency.repository.mysq;

import travelAgency.helper.PropertiesReader;
import travelAgency.repository.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDbConnection implements DbConnection {
    private String host;
    private String user;
    private String pass;
    private Connection connection;

    public MySQLDbConnection() {
        loadConfig();
    }

    private void loadConfig(){
        final PropertiesReader propertiesReader = new PropertiesReader("db-config");
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

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
