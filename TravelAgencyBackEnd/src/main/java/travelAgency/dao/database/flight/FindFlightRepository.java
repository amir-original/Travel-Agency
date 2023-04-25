package travelAgency.dao.database.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.dao.database.flight.FlightSQL.*;

public class FindFlightRepository {
    private final Connection connection;

    public FindFlightRepository(DbConnection db) {
        this.connection = db.getConnection();
    }

    public List<Flight> getFlights() {
        List<Flight> flights = new LinkedList<>();
        try (final PreparedStatement query = createQuery(GET_ALL_FLIGHTS_SQL)) {
            final ResultSet resultSet = query.executeQuery();
            getFlightsIfExists(flights, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    private void getFlightsIfExists(List<Flight> flights, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            flights.add(buildFlight(resultSet));
        }
    }

    public Optional<Flight> findFlight(String flightNumber) {
        Flight flight = null;
        try (final PreparedStatement query = createQuery(FIND_FLIGHT_BY_FLIGHT_NUMBER_SQL)) {
            query.setString(1, flightNumber);
            final ResultSet resultSet = query.executeQuery();
            flight = getFlightIfExist(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(flight);
    }

    private Flight getFlightIfExist(ResultSet resultSet) throws SQLException {
        Flight flight = null;
        if (resultSet.next()) {
            flight = buildFlight(resultSet);
        }
        return flight;
    }


    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
