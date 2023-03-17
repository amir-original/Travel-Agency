package travelAgency.repository.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.*;
import travelAgency.domain.city.City;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.domain.FlightBuilder.flight;
import static travelAgency.domain.PassengerBuilder.passenger;

public class BookingListRepositoryImpl implements BookingListRepository {
    private static final String SELECT_ALL = """
            SELECT t.ticket_number,t.number_of_tickets,f.*,p.* FROM tickets as t\s
                join flights as f on t.flight_number = f.flight_number
                join passengers as p on t.passenger_id = p.passenger_id
            """;

    private static final String SELECT_QUERY = SELECT_ALL + " where t.ticket_number = ?";

    private final DbConnection db;
    private final Connection connection;

    public BookingListRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public Optional<FlightTicket> ticket(String serialNumber) {
        FlightTicket flightTicket = null;
        try (final PreparedStatement sql = createQuery(SELECT_QUERY)) {
            sql.setString(1, serialNumber);
            final ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                flightTicket = getFlightTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(flightTicket);
    }

    @Override
    public List<FlightTicket> tickets() {
        List<FlightTicket> result = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL)) {
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                result.add(getFlightTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @NotNull
    private FlightTicket getFlightTicket(ResultSet rs) throws SQLException {
        final String ticket_number = rs.getString("ticket_number");
        final BookingInformation bookingInformation =
                new BookingInformation(getPassenger(rs),
                        rs.getInt("number_of_tickets"));

        return new FlightTicket(ticket_number, new FlightTicketInfo(getFlight(rs), bookingInformation));
    }

    private Passenger getPassenger(ResultSet rs) throws SQLException {
        return passenger()
                .withId(rs.getString("passenger_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .withBirthday(rs.getDate("birthday").toLocalDate())
                .ofCity(getCity(rs.getString("city")))
                .address(rs.getString("address"))
                .withZipcode(rs.getString("zipcode"))
                .withPhoneNumber(rs.getString("phone_number"))
                .build();
    }

    private Flight getFlight(ResultSet rs) throws SQLException {
        return flight()
                .withFlightNumber(rs.getString("flight_number"))
                .from(getCity(rs.getString("from_city")))
                .to(getCity(rs.getString("to_city")))
                .withPrice(rs.getDouble("price"))
                .departureAt(rs.getDate("departure").toLocalDate())
                .arrivalAt(rs.getDate("arrival").toLocalDate())
                .build();
    }

    private City getCity(String city) {
        return City.valueOf(city);
    }

    private PreparedStatement createQuery(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
