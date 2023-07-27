package travelAgency.infrastructure.persistence.jdbc_mysql.passenger;

import travelAgency.model.passenger.PassengerRepository;
import travelAgency.exceptions.CouldNotFoundPassenger;
import travelAgency.exceptions.CouldNotSavePassenger;
import travelAgency.exceptions.MainSQLException;
import travelAgency.model.passenger.Passenger;
import travelAgency.infrastructure.db.DbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerSQL.*;

public class PassengerRepositoryImpl implements PassengerRepository {

    private static final String TABLE_NAME = "passengers";

    private final DbConnection db;
    private final Connection connection;

    public PassengerRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.currentConnection();
    }


    @Override
    public void enroll(Passenger passenger) {
        try (final PreparedStatement query = createQuery(INSERT_PASSENGER_SQL)) {
            fillOutPassengerFields(passenger, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw CouldNotSavePassenger.becauseItIsDuplicate();
        }
    }

    @Override
    public void enroll(List<Passenger> passengers) {
        passengers.forEach(this::enroll);
    }

    @Override
    public Optional<Passenger> findPassengerById(String passengerId) {
        Passenger passenger;
        try (final PreparedStatement query = createQuery(FIND_PASSENGER_BY_ID_SQL)) {
            query.setString(1, passengerId);
            passenger = getPassengerIfExist(query.executeQuery());
        } catch (SQLException e) {
            throw CouldNotFoundPassenger.withPassengerId(passengerId);
        }
        return Optional.of(passenger);
    }

    private Passenger getPassengerIfExist(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            throw new SQLException();
        }
        return buildPassenger(rs);
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
