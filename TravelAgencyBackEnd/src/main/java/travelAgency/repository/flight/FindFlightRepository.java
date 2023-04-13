package travelAgency.repository.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.db.DbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static travelAgency.repository.flight.FlightSQL.*;
import static travelAgency.domain.flight.FlightBuilder.flight;

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

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return flight()
                .withFlightNumber(resultSet.getString("flight_number"))
                .from(City.valueOf(resultSet.getString("from_city")))
                .to(City.valueOf(resultSet.getString("to_city")))
                .departureAt(resultSet.getDate("departure").toLocalDate())
                .arrivalAt(resultSet.getDate("arrival").toLocalDate())
                .withPrice(resultSet.getDouble("price"))
                .withTotalCapacity(resultSet.getInt("total_capacity"))
                .build();
    }


    public void checkExistenceFlightWith(String flightNumber) {
        if (findFlight(flightNumber).isEmpty()) throw new NotFindAnyFlightException();
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
