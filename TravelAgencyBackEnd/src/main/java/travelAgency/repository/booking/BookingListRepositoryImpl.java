package travelAgency.repository.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.Reservation;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Reservation> findBooking(String ticketNumber) {
        return findBookingBy(ticketNumber, FIND_RESERVATION_BY_TICKET_NUMBER);
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

    private PreparedStatement createQuery(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
