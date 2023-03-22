package travelAgency.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import travelAgency.repository.db.mysq.MySQLDbConnection;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.fail;

public class DAOTest {

    @Test
    void connect_to_database_without_throw_any_exception() {
        final MySQLDbConnection dbConnection = new MySQLDbConnection();

        assertThatNoException().isThrownBy(dbConnection::getConnection);

        if (dbConnection.getConnection()==null) {
            fail("!! connect to database is fail !!");
        }
    }
}
