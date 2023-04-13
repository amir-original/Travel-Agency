package travelAgency.repository.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.Reservation;
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

    private static final String TABLE_NAME = "booking_list";
    private final DbConnection db;
    private final Connection connection;

    public BookingListRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public void book(Reservation reservation) {
        try (final PreparedStatement query = createQuery(INSERT_QUERY)) {
            fillFlightTicket(reservation, query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillFlightTicket(Reservation reservation, PreparedStatement query) throws SQLException {
        query.setString(1, reservation.ticketNumber());
        query.setString(2, reservation.flightNumber());
        query.setString(3, reservation.passengerId());
        query.setInt(4, reservation.travelers());
    }

    @Override
    public Optional<Reservation> findBooking(String tikcketNumber) {
        return findBookingBy(tikcketNumber, FIND_RESERVATION_BY_TICKET_NUMBER);
    }

    @Override
    public Optional<Reservation> findBookingByFlightNumber(String flightNumber) {
        return findBookingBy(flightNumber, FIND_RESERVATION_BY_FLIGHT_NUMBER);
    }

    @NotNull
    private Optional<Reservation> findBookingBy(String flightNumber, String sql) {
        Reservation reservation = null;
        try (final PreparedStatement query = createQuery(sql)) {
            query.setString(1, flightNumber);
            final ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                reservation = buildFlightTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public List<Reservation> getAllBookings() {
        List<Reservation> result = new LinkedList<>();
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
    public void cancel(String ticketNumber) {
        try (final PreparedStatement query = createQuery(CANCEL_BOOKING)) {
            query.setString(1, ticketNumber);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private Reservation buildFlightTicket(ResultSet rs) throws SQLException {
        final String ticket_number = rs.getString("ticket_number");

        final BookingInformation bookingInformation =
                new BookingInformation(buildFlight(rs),
                        buildPassenger(rs),
                        rs.getInt("number_of_tickets"));

        return new Reservation(ticket_number, bookingInformation);
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
