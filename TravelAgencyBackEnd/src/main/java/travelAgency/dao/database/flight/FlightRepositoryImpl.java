package travelAgency.dao.database.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static travelAgency.dao.database.flight.FlightSQL.*;


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
        try (final PreparedStatement query = createQuery(INSERT_FLIGHT_SQL)) {
            filledFlightFields(flight, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new SQLFlightInsertionException(e.getMessage());
        }
    }

    @Override
    public void deleteFlight(Flight flight) {
        try (final PreparedStatement query = createQuery(DELETE_FLIGHT_SQL)) {
            query.setString(1, flight.flightNumber());
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
}
