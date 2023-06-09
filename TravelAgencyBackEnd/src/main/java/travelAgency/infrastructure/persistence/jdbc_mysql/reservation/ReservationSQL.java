package travelAgency.infrastructure.persistence.jdbc_mysql.reservation;

import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationNumber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static travelAgency.infrastructure.persistence.jdbc_mysql.flight.FlightSQL.buildFlight;
import static travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerSQL.buildPassenger;

public class ReservationSQL {

    public static final String TABLE_SCHEMA = """
            CREATE TABLE IF NOT EXISTS `reservation` (
            `id` INT UNSIGNED AUTO_INCREMENT,
            `ticket_number` VARCHAR(45) NOT NULL,
            `flight_number` VARCHAR(45) NOT NULL,
            `passenger_id` varchar(174) NOT NULL,
            `number_of_tickets` INT UNSIGNED NOT NULL,
            PRIMARY KEY (`ticket_number`),
            UNIQUE KEY `ticket_id_UNIQUE` (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
            """;
    public static final String INSERT_RESERVATION_SQL = """
            INSERT INTO reservation
            (ticket_number,flight_number,passenger_id,number_of_tickets)
            VALUES (?,?,?,?)
            """;
    public static final String GET_ALL_RESERVATIONS = """
            SELECT t.ticket_number,t.number_of_tickets,f.*,p.* FROM reservation as t
                join flights as f on t.flight_number = f.flight_number
                join passengers as p on t.passenger_id = p.passenger_id
            """;

    public static final String FIND_RESERVATION_BY_RESERVATION_NUMBER =
            GET_ALL_RESERVATIONS + " WHERE t.ticket_number = ?";

    public static final String CANCEL_RESERVATION =
            "DELETE FROM reservation WHERE ticket_number=?";

    public static String FIND_RESERVATION_BY_FLIGHT_NUMBER =
            FIND_RESERVATION_BY_RESERVATION_NUMBER + "WHERE f.flight_number = ?";


    public static void fillOutReservationFields(Reservation reservation, PreparedStatement query) throws SQLException {
        query.setString(1, reservation.reservationNumber());
        query.setString(2, reservation.flightNumber());
        query.setString(3, reservation.passengerId());
        query.setInt(4, reservation.travelers());
    }


    public static Reservation buildReservation(ResultSet rs) throws SQLException {
        final String ticketNumber = rs.getString("ticket_number");
        final ReservationNumber reservationNumber = ReservationNumber.ofString(ticketNumber);
        final Flight flight = buildFlight(rs);
        final Passenger passenger = buildPassenger(rs);
        final int numberOfTickets = rs.getInt("number_of_tickets");

        return Reservation.make(reservationNumber, flight, passenger, numberOfTickets);
    }

}
