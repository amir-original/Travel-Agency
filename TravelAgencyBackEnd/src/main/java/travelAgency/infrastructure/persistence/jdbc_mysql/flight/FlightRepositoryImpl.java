package travelAgency.infrastructure.persistence.jdbc_mysql.flight;

import travelAgency.infrastructure.db.DbConnection;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static travelAgency.infrastructure.persistence.jdbc_mysql.flight.FlightSQL.*;


public class FlightRepositoryImpl implements FlightRepository {
    private static final String TABLE_NAME = "flights";

    private final DbConnection db;
    private final Connection connection;
    private final FindFlightRepository findFlightRepository;

    public FlightRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.currentConnection();
        this.findFlightRepository = new FindFlightRepository(db);
    }


    @Override
    public void addFlights(List<Flight> flights) {
        flights.forEach(this::addFlight);
    }

    @Override
    public void addFlight(Flight flight) {
        try (final PreparedStatement query = createQuery(INSERT_FLIGHT_SQL)) {
            filledOutFlightFields(flight, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw CouldNotStoreFlight.becauseItIsDuplicate();
        }
    }

    @Override
    public void deleteFlight(Flight flight) {
        try (final PreparedStatement query = createQuery(DELETE_FLIGHT_SQL)) {
            query.setString(1, flight.flightNumber());
            query.executeUpdate();
        } catch (SQLException e) {
            throw CouldNotDeleteFlight.withFlightNumber(flight.flightNumber());
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
