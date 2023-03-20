package travelAgency.repository.flight;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.db.DbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.domain.FlightBuilder.flight;

public class FindFlightRepositoryImpl implements FindFlightRepository{

    private static final String SELECT_QUERY = "SELECT * FROM flights WHERE flight_number = ?";
    private static final String SELECT_ALL_FIND_FLIGHTS =
            "SELECT * FROM flights where from_city = ? and to_city = ? and departure = ? and arrival=?";

    private DbConnection db;
    private Connection connection;

    public FindFlightRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public List<Flight> getFlights() {
        return null;
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightPlan) {
        List<Flight> result = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL_FIND_FLIGHTS)) {
            filledFlightPlan(flightPlan, query);
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()){
                result.add(buildFlight(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void filledFlightPlan(FlightPlan flightPlan, PreparedStatement query) throws SQLException {
        query.setString(1, flightPlan.from().name());
        query.setString(2, flightPlan.to().name());
        query.setDate(3, Date.valueOf(flightPlan.departure()));
        query.setDate(4, Date.valueOf(flightPlan.arrival()));
    }

    @Override
    public Optional<Flight> findFlight(String flightNumber) {
        Flight flight = null;
        try (final PreparedStatement query = createQuery(SELECT_QUERY)) {
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
                .build();
    }

    @Override
    public void checkExistenceFlightWith(String flightNumber) {
        if (findFlight(flightNumber).isEmpty()) throw new NotFindAnyFlightException();
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
