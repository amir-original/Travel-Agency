package travelAgency.dao.database.flight;

import travelAgency.exceptions.MainSQLException;
import travelAgency.domain.flight.Flight;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
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
           throw new MainSQLException(e.getMessage());
        }
        return flights;
    }

    private void getFlightsIfExists(List<Flight> flights, ResultSet rs) throws SQLException {
        while (rs.next()) {
            final Flight flight = buildFlight(rs);
            flights.add(flight);
        }
    }

    public Optional<Flight> findFlight(String flightNumber) {
        Flight flight;
        try (final PreparedStatement query = createQuery(FIND_FLIGHT_BY_FLIGHT_NUMBER_SQL)) {
            query.setString(1, flightNumber);
            final ResultSet rs = query.executeQuery();
            flight = getFlightIfExist(rs);
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
        return ofNullable(flight);
    }

    private Flight getFlightIfExist(ResultSet rs) throws SQLException {
        Flight flight = null;
        if (rs.next()) {
            flight = buildFlight(rs);
        }
        return flight;
    }


    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
