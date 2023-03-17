package travelAgency.repository.flight;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightBuilder;
import travelAgency.domain.city.City;
import travelAgency.repository.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FlightRepositoryImpl implements FlightRepository {
    private static final String TABLE_NAME = "flights";
    private static final String INSERT_SQL = """
            INSERT INTO flights (flight_number,from_city,to_city,departure,arrival,price)
             VALUES (?,?,?,?,?,?)
            """;
    private static final String DELETE_SQL = "DELETE FROM flights WHERE flight_number = ?";

    private static final String SELECT_QUERY = "SELECT * FROM flights WHERE flight_number = ?";

    private static final String SELECT_ALL = "SELECT * FROM flights";

    private final DbConnection db;
    private final Connection connection;

    public FlightRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public void createFlight(Flight flight) {
        try (final PreparedStatement insert = createQuery(INSERT_SQL)) {
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
        insert.setDate(4, getSQLDate(flight.departure()));
        insert.setDate(5, getSQLDate(flight.arrival()));
        insert.setDouble(6, flight.price());
    }

    private Date getSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    @Override
    public void createFlights(List<Flight> flights) {
        flights.forEach(this::createFlight);
    }

    @Override
    public Optional<Flight> flight(String serialNumber) {
        Flight flight = null;
        try (final PreparedStatement query = createQuery(SELECT_QUERY)) {
            query.setString(1, serialNumber);
            final ResultSet result = query.executeQuery();
            while (result.next()) {
                flight = getFlight(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(flight);
    }

    private Flight getFlight(ResultSet result) throws SQLException {

        return FlightBuilder.flight()
                .withFlightNumber(result.getString("flight_number"))
                .from(City.valueOf(result.getString("from_city")))
                .to(City.valueOf(result.getString("to_city")))
                .departureAt(result.getDate("departure").toLocalDate())
                .arrivalAt(result.getDate("arrival").toLocalDate())
                .withPrice(result.getDouble("price"))
                .build();
    }

    @Override
    public void deleteFlight(String flightNumber) {
        try (final PreparedStatement query = createQuery(DELETE_SQL)) {
            query.setString(1, flightNumber);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Flight> flights() {
        List<Flight> flights = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL)) {
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                flights.add(getFlight(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String s) throws SQLException {
        return connection.prepareStatement(s);
    }
}
