package travelAgency.dao.database.passenger;

import travelAgency.exceptions.MainSQLException;
import travelAgency.domain.passenger.Passenger;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.dao.database.passenger.PassengerSQL.*;

public class PassengerRepositoryImpl implements PassengerRepository {

    private static final String TABLE_NAME = "passengers";

    private final DbConnection db;
    private final Connection connection;

    public PassengerRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
        activeDeletedTrigger();
    }


    @Override
    public void save(Passenger passenger) {
        try (final PreparedStatement query = createQuery(INSERT_PASSENGER_SQL)) {
            fillOutPassengerFields(passenger, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
    }

    @Override
    public void save(List<Passenger> passengers) {
        passengers.forEach(this::save);
    }

    @Override
    public Optional<Passenger> findPassengerById(String passengerId) {
        Passenger passenger;
        try (final PreparedStatement query = createQuery(FIND_PASSENGER_BY_ID_SQL)) {
            query.setString(1, passengerId);
            passenger = getPassengerIfExist(query.executeQuery());

        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
        return Optional.ofNullable(passenger);
    }

    private Passenger getPassengerIfExist(ResultSet rs) throws SQLException {
        Passenger result = null;
        if (rs.next()) {
            result = buildPassenger(rs);
        }
        return result;
    }

    @Override
    public List<Passenger> getPassengers() {
        List<Passenger> passengers;
        try (final PreparedStatement query = createQuery(GET_ALL_PASSENGERS_SQL)) {
            passengers = getPassengersIfExists(query.executeQuery());
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
        return passengers;
    }

    private List<Passenger> getPassengersIfExists(ResultSet rs) throws SQLException {
        List<Passenger> passengers = new LinkedList<>();
        while (rs.next()) {
            passengers.add(buildPassenger(rs));
        }
        return passengers;
    }

    private void activeDeletedTrigger() {
        dropTrigger();
        try (final PreparedStatement query = createQuery(CREATE_DELETED_TRIGGER)) {
            query.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
    }

    private void dropTrigger() {
        try (final PreparedStatement query = createQuery(DROP_TRIGGER)) {
            query.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
    }

    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
