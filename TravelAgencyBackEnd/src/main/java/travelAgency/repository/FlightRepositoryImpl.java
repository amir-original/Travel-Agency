package travelAgency.repository;

import travelAgency.domain.Flight;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.time.LocalDate.of;

public class FlightRepositoryImpl implements FlightRepository {
    private final DbConnection connection;
    private static final String INSERT_SQL =
            "INSERT INTO flights (serial_number,from_city,to_city,departure,arrival,price) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM flights where serial_number = ?";

    public FlightRepositoryImpl(DbConnection connection) {
        this.connection = connection;
    }

    @Override
    public void addFlight(Flight flight) {
        try (final PreparedStatement insert = connection.getConnection().prepareStatement(INSERT_SQL)) {
            filledFlightFields(flight, insert);
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    private void filledFlightFields(Flight flight, PreparedStatement insert) throws SQLException {
        insert.setString(1, flight.getSerialNumber());
        insert.setString(2, flight.from().name());
        insert.setString(3, flight.to().name());
        insert.setDate(4, getDate(flight.departure()));
        insert.setDate(5, getDate(flight.arrival()));
        insert.setDouble(6, flight.getPrice());
    }

    private Date getDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    @Override
    public void deleteFlight(Flight flight) {
        try (final PreparedStatement delete = connection.getConnection().prepareStatement(DELETE_SQL)) {
            delete.setString(1,flight.getSerialNumber());
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
