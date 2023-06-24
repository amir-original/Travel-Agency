package travelAgency.dao.database.db_config.mysql;

import travelAgency.dao.database.db_config.ConnectionConfiguration;
import travelAgency.dao.database.flight.FlightSQL;
import travelAgency.dao.database.passenger.PassengerSQL;
import travelAgency.dao.database.reservation.ReservationSQL;
import travelAgency.exceptions.CouldNotCreateFlightTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

public class MySQLDbConnection implements DbConnection {

    private final ConnectionConfiguration configuration;
    private Connection connection;

    public MySQLDbConnection(ConnectionConfiguration initConfiguration) {
        this.configuration = requireNonNull(initConfiguration);
        createTablesIfNotExists();
    }

    public Connection currentConnection() {
        if (connection != null) return connection;

        try {
            Class.forName(configuration.driver());
            connection = DriverManager.getConnection(
                    configuration.url(),
                    configuration.username(),
                    configuration.password());
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new CouldNotConnectToDatabase();
        }
    }

    private void createTablesIfNotExists() {
        createTable(PassengerSQL.TABLE_SCHEMA);
        createTable(FlightSQL.TABLE_SCHEMA);
        createTable(ReservationSQL.TABLE_SCHEMA);
    }

    @Override
    public void createTable(String tableSchema) {
        try (final PreparedStatement query = createQuery(tableSchema)) {
            query.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotCreateFlightTable();
        }
    }

    private PreparedStatement createQuery(String s) throws SQLException {
        return currentConnection().prepareStatement(s);
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
