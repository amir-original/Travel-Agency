package travelAgency.repository.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.repository.flight.FlightSQL.*;

public class FindFlightRepository {
    private final Connection connection;

    public FindFlightRepository(DbConnection db) {
        this.connection = db.getConnection();
    }



    public List<Flight> getFlights() {
        List<Flight> flights = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL)) {
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }


    public Optional<Flight> findFlight(String flightNumber) {
        Flight flight = null;
        try (final PreparedStatement query = createQuery(SELECT_WHERE)) {
            query.setString(1, flightNumber);
            final ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                flight = buildFlight(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(flight);
    }


    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
