package travelAgency.dao;

import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.infrastructure.io.PropertiesReader;

import static org.assertj.core.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    void connect_to_database_without_throw_any_exception() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        final MySQLDbConnection dbConnection = new MySQLDbConnection(configuration);

        assertThatNoException().isThrownBy(dbConnection::currentConnection);

        if (dbConnection.currentConnection()==null) {
            fail("!! connect to database is fail !!");
        }
    }

    /*@Test
    void throw_CouldNotConnectToDatabase_when_enter_wrong_db_config() {
        final MySQLDbConnection dbConnection = new MySQLDbConnection(HOST, USER, PASS);

        assertThatExceptionOfType(CouldNotConnectToDatabase.class).isThrownBy(dbConnection::currentConnection);
    }*/
}
