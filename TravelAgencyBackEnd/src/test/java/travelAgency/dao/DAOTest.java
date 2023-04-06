package travelAgency.dao;

import org.junit.jupiter.api.Test;
import travelAgency.repository.db.mysq.DatabaseConnectionException;
import travelAgency.repository.db.mysq.MySQLDbConnection;

import static org.assertj.core.api.Assertions.*;

public class DAOTest {

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
    void throw_Exception_when_enter_wrong_db_config() {
        final MySQLDbConnection dbConnection = new MySQLDbConnection(HOST, USER, PASS);

        assertThatExceptionOfType(DatabaseConnectionException.class).isThrownBy(dbConnection::getConnection);
    }
}
