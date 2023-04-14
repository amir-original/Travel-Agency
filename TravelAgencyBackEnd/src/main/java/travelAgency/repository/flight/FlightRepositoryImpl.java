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
    private final FindFlightRepository findFlightRepository;

    public FlightRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
        this.findFlightRepository = new FindFlightRepository(db);
    }

    @Override
    public void addFlights(List<Flight> flights) {
        flights.forEach(this::addFlight);
    }

    @Override
    public void addFlight(Flight flight) {
        try (final PreparedStatement query = createQuery(INSERT_QUERY)) {
            filledFlightFields(flight, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new SQLFlightInsertionException(e.getMessage());
        }
    }

    private void filledFlightFields(Flight flight, PreparedStatement query) throws SQLException {
        query.setString(1, flight.flightNumber());
        query.setString(2, flight.from().name());
        query.setString(3, flight.to().name());
        query.setDate(4, convertToSQLDate(flight.departure()));
        query.setDate(5, convertToSQLDate(flight.arrival()));
        query.setInt(6, flight.totalCapacity());
        query.setDouble(7, flight.price());
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
    public Optional<Flight> findFlight(String flightNumber) {
        return findFlightRepository.findFlight(flightNumber);
    }

    @Override
    public List<Flight> flights() {
        return findFlightRepository.getFlights();
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String s) throws SQLException {
        return connection.prepareStatement(s);
    }

    private Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
