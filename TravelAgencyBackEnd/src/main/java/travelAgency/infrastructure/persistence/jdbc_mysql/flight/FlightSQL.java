package travelAgency.infrastructure.persistence.jdbc_mysql.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.city.City;
import travelAgency.model.rate.currency.Currency;
import travelAgency.model.flight.Flight;
import travelAgency.model.rate.currency.Money;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

import static travelAgency.model.flight.FlightBuilder.flight;

public class FlightSQL {

    public static final String TABLE_SCHEMA = """
            CREATE TABLE IF NOT EXISTS flights (
                id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                flight_number VARCHAR(45) NOT NULL,
                from_city VARCHAR(45) NOT NULL,
                to_city VARCHAR(45) NOT NULL,
                departure DATE NOT NULL,
                arrival DATE NOT NULL DEFAULT '0000-00-00',
                total_capacity INT(10) UNSIGNED NOT NULL,
                price DOUBLE NOT NULL,
                currency VARCHAR(45) NOT NULL,
                UNIQUE KEY (flight_number),
                UNIQUE KEY (id)
              ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
            """;
    static final String INSERT_FLIGHT_SQL = """
            INSERT INTO flights (flight_number,
            from_city,
            to_city,
            departure,
            arrival,
            total_capacity,
            price,
            currency)
             VALUES (?,?,?,?,?,?,?,?)
            """;
    static final String DELETE_FLIGHT_SQL
            = "DELETE FROM flights WHERE flight_number = ?";
    static final String GET_ALL_FLIGHTS_SQL
            = "SELECT * FROM flights";
    static final String FIND_FLIGHT_BY_FLIGHT_NUMBER_SQL
            = "SELECT * FROM flights WHERE flight_number = ?";

    public static void filledOutFlightFields(Flight flight, PreparedStatement query) throws SQLException {
        query.setString(1, flight.flightNumber());
        query.setString(2, flight.from().name());
        query.setString(3, flight.to().name());
        query.setDate(4, convertToSQLDate(flight.departure()));
        query.setDate(5, convertToSQLDate(flight.arrival()));
        query.setInt(6, flight.totalCapacity());
        query.setDouble(7, flight.price().amount());
        query.setString(8, flight.price().currency().name());
    }

    public static Flight buildFlight(ResultSet resultSet) throws SQLException {
        return flight()
                .withFlightNumber(resultSet.getString("flight_number"))
                .from(City.valueOf(resultSet.getString("from_city").toUpperCase()))
                .to(City.valueOf(resultSet.getString("to_city").toUpperCase(Locale.ROOT)))
                .departureAt(resultSet.getDate("departure").toLocalDate())
                .arrivalAt(resultSet.getDate("arrival").toLocalDate())
                .withPrice(getPrice(resultSet))
                .withTotalCapacity(resultSet.getInt("total_capacity"))
                .build();
    }

    @NotNull
    private static Money getPrice(ResultSet resultSet) throws SQLException {
        return Money.of(resultSet.getLong("price"),
                getCurrency(resultSet.getString("currency")));
    }

    @NotNull
    private static Currency getCurrency(String value) {
        return Currency.valueOf(value);
    }

    private static Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
