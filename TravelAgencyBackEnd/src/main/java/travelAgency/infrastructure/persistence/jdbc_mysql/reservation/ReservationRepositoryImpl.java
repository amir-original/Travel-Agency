package travelAgency.infrastructure.persistence.jdbc_mysql.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.exceptions.CouldNotBookReservation;
import travelAgency.exceptions.CouldNotFoundReservation;
import travelAgency.exceptions.MainSQLException;
import travelAgency.model.reservation.Reservation;
import travelAgency.infrastructure.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static travelAgency.infrastructure.persistence.jdbc_mysql.reservation.ReservationSQL.*;

public class ReservationRepositoryImpl implements ReservationRepository {

    private static final String TABLE_NAME = "reservation";
    private final DbConnection db;
    private final Connection connection;

    public ReservationRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.currentConnection();
    }

    @Override
    public void book(Reservation reservation) {
        try (final PreparedStatement query = createQuery(INSERT_RESERVATION_SQL)) {
            fillOutReservationFields(reservation, query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw CouldNotBookReservation.becauseItIsDuplicate();
        }
    }

    @Override
    public Optional<Reservation> findReservation(String reservationNumber) {
        return findBy(reservationNumber, FIND_RESERVATION_BY_RESERVATION_NUMBER);
    }

    @Override
    public Optional<Reservation> findReservationByFlightNumber(String flightNumber) {
        return findBy(flightNumber, FIND_RESERVATION_BY_FLIGHT_NUMBER);
    }

    @NotNull
    private Optional<Reservation> findBy(String reservationNumber, String sql) {
        Reservation reservation;
        try (final PreparedStatement query = createQuery(sql)) {
            query.setString(1, reservationNumber);
            final ResultSet resultSet = query.executeQuery();
            reservation = getReservationIfExist(resultSet);
        } catch (SQLException e) {
            throw new CouldNotFoundReservation(e.getMessage());
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
            throw new MainSQLException(e.getMessage());
        }
        return result;
    }

    private void findReservationsIfExists(List<Reservation> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            result.add(buildReservation(resultSet));
        }
    }

    @Override
    public void cancel(String reservationNumber) {
        try (final PreparedStatement query = createQuery(CANCEL_RESERVATION)) {
            query.setString(1, reservationNumber);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e.getMessage());
        }
    }

    @Override
    public void cancel(Reservation reservation) {
        cancel(reservation.reservationNumber());
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }

    private PreparedStatement createQuery(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
