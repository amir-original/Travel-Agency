package travelAgency.repository.passenger;

import travelAgency.domain.Passenger;
import travelAgency.domain.city.City;
import travelAgency.repository.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.domain.PassengerBuilder.passenger;
import static travelAgency.repository.passenger.PassengerSQL.*;

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
        try (final PreparedStatement query = createQuery(INSERT_QUERY)) {
            fillPassengerField(passenger, query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<Passenger> passengers) {
        passengers.forEach(this::save);
    }

    private void fillPassengerField(Passenger passenger, PreparedStatement sql) throws SQLException {
        sql.setString(1, passenger.id());
        sql.setString(2, passenger.fName());
        sql.setString(3, passenger.lName());
        sql.setDate(4, convertToSQLDate(passenger.birthday()));
        sql.setString(5, passenger.city().name());
        sql.setString(6, passenger.address());
        sql.setString(7, passenger.zipcode());
        sql.setString(8, passenger.phoneNumber());
    }

    private Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    private void activeDeletedTrigger() {
        dropTrigger();
        try (final PreparedStatement query = createQuery(CREATE_DELETED_TRIGGER)) {
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTrigger() {
        try (final PreparedStatement statement = createQuery(DROP_TRIGGER)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Passenger> getPassenger(String passengerId) {
        Passenger passenger = null;
        try (final PreparedStatement query = createQuery(SELECT_PASSENGER)) {
            query.setString(1, passengerId);

            passenger = findPassengerIfExist(query.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(passenger);
    }

    private Passenger findPassengerIfExist(ResultSet rs) throws SQLException {
        Passenger result = null;
        if (rs.next()) {
            result = buildPassenger(rs);
        }
        return result;
    }

    @Override
    public List<Passenger> getPassengers() {
        List<Passenger> passengers = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL)) {
            passengers = findPassengersIfExists(query.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    private List<Passenger> findPassengersIfExists(ResultSet rs) throws SQLException {
        List<Passenger> passengers = new LinkedList<>();
        while (rs.next()) {
            passengers.add(buildPassenger(rs));
        }
        return passengers;
    }

    private Passenger buildPassenger(ResultSet rs) throws SQLException {
        return passenger()
                .withId(rs.getString("passenger_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .withBirthday(rs.getDate("birthday").toLocalDate())
                .withZipcode(rs.getString("zipcode"))
                .ofCity(City.valueOf(rs.getString("city")))
                .address(rs.getString("address"))
                .withPhoneNumber(rs.getString("phone_number"))
                .build();

    }

    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
