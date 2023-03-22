package travelAgency.repository.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.repository.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static travelAgency.repository.flight.FlightSQL.*;


public class FlightRepositoryImpl implements FlightRepository {
    private static final String TABLE_NAME = "flights";

    private final DbConnection db;
    private final Connection connection;
    private FindFlightRepository findFlightRepository;

    public FlightRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
        this.findFlightRepository = new FindFlightRepositoryImpl(db);
    }

    @Override
    public void addFlights(List<Flight> flights) {
        flights.forEach(this::addFlight);
    }

    @Override
    public void addFlight(Flight flight) {
        try (final PreparedStatement insert = createQuery(INSERT_QUERY)) {
            filledFlightFields(flight, insert);
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new FlightRepositorySQLException();
        }
    }

    private void filledFlightFields(Flight flight, PreparedStatement insert) throws SQLException {
        insert.setString(1, flight.flightNumber());
        insert.setString(2, flight.from().name());
        insert.setString(3, flight.to().name());
        insert.setDate(4, convertToSQLDate(flight.departure()));
        insert.setDate(5, convertToSQLDate(flight.arrival()));
        insert.setDouble(6, flight.price());
    }

    private Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        try (final PreparedStatement query = createQuery(DELETE_QUERY)) {
            query.setString(1, flightNumber);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Flight> flight(String flightNumber) {
        return findFlightRepository.findFlight(flightNumber);
    }

    @Override
    public List<Flight> flights() {
        return findFlightRepository.getFlights();
    }

    @Override
    public void checkExistenceFlightWith(String flightNumber) {
        findFlightRepository.checkExistenceFlightWith(flightNumber);
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String s) throws SQLException {
        return connection.prepareStatement(s);
    }
}
