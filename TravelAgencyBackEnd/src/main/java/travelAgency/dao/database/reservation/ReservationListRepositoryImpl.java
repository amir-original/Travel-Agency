package travelAgency.dao.database.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.Reservation;
import travelAgency.dao.database.db_config.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.dao.database.reservation.ReservationSQL.*;

public class ReservationListRepositoryImpl implements ReservationListRepository {

    private static final String TABLE_NAME = "reservation";
    private final DbConnection db;
    private final Connection connection;

    public ReservationListRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public void book(Reservation reservation) {
        try (final PreparedStatement query = createQuery(INSERT_RESERVATION_SQL)) {
            fillOutReservationFields(reservation, query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Reservation> findReservation(String reservationNumber) {
        return findReservationBy(reservationNumber, FIND_RESERVATION_BY_TICKET_NUMBER);
    }

    @Override
    public Optional<Reservation> findReservationByFlightNumber(String flightNumber) {
        return findReservationBy(flightNumber, FIND_RESERVATION_BY_FLIGHT_NUMBER);
    }

    @NotNull
    private Optional<Reservation> findReservationBy(String flightNumber, String sql) {
        Reservation reservation = null;
        try (final PreparedStatement query = createQuery(sql)) {
            query.setString(1, flightNumber);
            final ResultSet resultSet = query.executeQuery();
            reservation = getReservationIfExist(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(reservation);
    }

    private Reservation getReservationIfExist(ResultSet rs) throws SQLException {
        Reservation result = null;
        if (rs.next()) {
            result = buildReservation(rs);
        }
        return result;
    }

    @Override
    public List<Reservation> getReservations() {
        List<Reservation> result = new LinkedList<>();
        try (final PreparedStatement query = createQuery(GET_ALL_RESERVATIONS)) {
            final ResultSet resultSet = query.executeQuery();
            findReservationsIfExists(result, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void findReservationsIfExists(List<Reservation> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            result.add(buildReservation(resultSet));
        }
    }

    @Override
    public void cancel(String ticketNumber) {
        try (final PreparedStatement query = createQuery(CANCEL_RESERVATION)) {
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
