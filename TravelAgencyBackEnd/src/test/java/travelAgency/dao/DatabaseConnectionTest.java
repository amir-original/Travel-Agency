package travelAgency.dao;

import org.junit.jupiter.api.Test;
import travelAgency.dao.database.db_config.mysql.CouldNotConnectToDatabase;
import travelAgency.dao.database.db_config.mysql.MySQLDbConnection;

import static org.assertj.core.api.Assertions.*;

public class DatabaseConnectionTest {

    public static final String HOST = "jdbc:mysql://localhost:3306/wrong";
    public static final String USER = "wrong_username";
    private static final String PASS = "wrong_password";

    @Test
    void connect_to_database_without_throw_any_exception() {
        final MySQLDbConnection dbConnection = new MySQLDbConnection();

        assertThatNoException().isThrownBy(dbConnection::getConnection);

        if (dbConnection.getConnection()==null) {
            fail("!! connect to database is fail !!");
        }
    }

    @Test
    void throw_CouldNotConnectToDatabase_when_enter_wrong_db_config() {
        final MySQLDbConnection dbConnection = new MySQLDbConnection(HOST, USER, PASS);

        assertThatExceptionOfType(CouldNotConnectToDatabase.class).isThrownBy(dbConnection::getConnection);
    }
}
