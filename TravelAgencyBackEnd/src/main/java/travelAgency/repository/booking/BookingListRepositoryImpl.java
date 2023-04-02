package travelAgency.repository.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.city.City;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.domain.flight.FlightBuilder.flight;
import static travelAgency.domain.passenger.PassengerBuilder.passenger;
import static travelAgency.repository.booking.BookingSQL.*;

public class BookingListRepositoryImpl implements BookingListRepository {
    private final DbConnection db;
    private final Connection connection;

    public BookingListRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public void book(FlightTicket flightTicket) {
        try (final PreparedStatement query = createQuery(INSERT_QUERY)) {
            fillFlightTicket(flightTicket, query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillFlightTicket(FlightTicket flightTicket, PreparedStatement query) throws SQLException {
        query.setString(1, flightTicket.ticketNumber());
        query.setString(2, flightTicket.flightNumber());
        query.setString(3, flightTicket.passenger_id());
        query.setInt(4, flightTicket.travelers());
    }

    @Override
    public Optional<FlightTicket> findBooking(String tikcketNumber) {
        FlightTicket flightTicket = null;
        try (final PreparedStatement sql = createQuery(SELECT_JOIN_WHERE)) {
            sql.setString(1, tikcketNumber);
            final ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()) {
                flightTicket = buildFlightTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(flightTicket);
    }

    @Override
    public List<FlightTicket> findBookings(String flightNumber) {
        List<FlightTicket> result = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL_JOIN_BY_FLIGHT_NUMBER)) {
            query.setString(1, flightNumber);
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                result.add(buildFlightTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<FlightTicket> getAllBookings() {
        List<FlightTicket> result = new LinkedList<>();
        try (final PreparedStatement query = createQuery(SELECT_ALL_JOIN)) {
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                result.add(buildFlightTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cancel(FlightTicket flightTicket) {

    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    @NotNull
    private FlightTicket buildFlightTicket(ResultSet rs) throws SQLException {
        final String ticket_number = rs.getString("ticket_number");

        final BookingInformation bookingInformation =
                new BookingInformation(buildFlight(rs),
                        buildPassenger(rs),
                        rs.getInt("number_of_tickets"));

        return new FlightTicket(ticket_number, bookingInformation);
    }

    private Passenger buildPassenger(ResultSet rs) throws SQLException {
        return passenger()
                .withId(rs.getString("passenger_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .withBirthday(rs.getDate("birthday").toLocalDate())
                .ofCity(getCity(rs.getString("city")))
                .withAddress(rs.getString("address"))
                .withZipcode(rs.getString("zipcode"))
                .withPhoneNumber(rs.getString("phone_number"))
                .build();
    }

    private Flight buildFlight(ResultSet rs) throws SQLException {
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
