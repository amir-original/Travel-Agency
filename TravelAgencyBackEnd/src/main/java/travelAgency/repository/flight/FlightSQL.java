package travelAgency.repository.flight;

import travelAgency.domain.city.City;
import travelAgency.domain.flight.Flight;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static travelAgency.domain.flight.FlightBuilder.flight;

public class FlightSQL {

    public static final String INSERT_QUERY = """
            INSERT INTO flights (flight_number,from_city,to_city,departure,arrival,total_capacity,price)
             VALUES (?,?,?,?,?,?,?)
            """;
    public static final String DELETE_QUERY = "DELETE FROM flights WHERE flight_number = ?";

    public static final String SELECT_ALL = "SELECT * FROM flights";
    public static final String SELECT_WHERE = "SELECT * FROM flights WHERE flight_number = ?";

    public static void filledFlightFields(Flight flight, PreparedStatement query) throws SQLException {
        query.setString(1, flight.flightNumber());
        query.setString(2, flight.from().name());
        query.setString(3, flight.to().name());
        query.setDate(4, convertToSQLDate(flight.departure()));
        query.setDate(5, convertToSQLDate(flight.arrival()));
        query.setInt(6, flight.totalCapacity());
        query.setDouble(7, flight.price());
    }

    public static Flight buildFlight(ResultSet resultSet) throws SQLException {
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

    private static Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
