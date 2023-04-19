package travelAgency.repository.booking;

import travelAgency.domain.reservation.BookingInformation;
import travelAgency.domain.reservation.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static travelAgency.repository.flight.FlightSQL.buildFlight;
import static travelAgency.repository.passenger.PassengerSQL.buildPassenger;

public class BookingSQL {
    public static final String SELECT_ALL_JOIN = """
            SELECT t.ticket_number,t.number_of_tickets,f.*,p.* FROM booking_list as t
                join flights as f on t.flight_number = f.flight_number
                join passengers as p on t.passenger_id = p.passenger_id
            """;

    public static final String INSERT_QUERY = """
            INSERT INTO booking_list
            (ticket_number,flight_number,passenger_id,number_of_tickets)
            VALUES (?,?,?,?)
            """;

    public static final String FIND_RESERVATION_BY_TICKET_NUMBER =
            SELECT_ALL_JOIN + " WHERE t.ticket_number = ?";

    public static final String CANCEL_BOOKING =
            "delete from booking_list where ticket_number=?";

    public static String FIND_RESERVATION_BY_FLIGHT_NUMBER =
            FIND_RESERVATION_BY_TICKET_NUMBER + "WHERE f.flight_number = ?";


    public static void fillFlightTicket(Reservation reservation, PreparedStatement query) throws SQLException {
        query.setString(1, reservation.ticketNumber());
        query.setString(2, reservation.flightNumber());
        query.setString(3, reservation.passengerId());
        query.setInt(4, reservation.travelers());
    }


    public static Reservation buildFlightTicket(ResultSet rs) throws SQLException {
        final String ticket_number = rs.getString("ticket_number");

        final BookingInformation bookingInformation =
                new BookingInformation(buildFlight(rs),
                        buildPassenger(rs),
                        rs.getInt("number_of_tickets"));

        return new Reservation(ticket_number, bookingInformation);
    }
}
