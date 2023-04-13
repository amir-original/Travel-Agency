package travelAgency.repository.booking;

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

    public static final String FIND_RESERVATION_BY_TICKET_NUMBER = SELECT_ALL_JOIN + " WHERE t.ticket_number = ?";
    public static final String CANCEL_BOOKING = "delete from booking_list where ticket_number=?";
    public static String FIND_RESERVATION_BY_FLIGHT_NUMBER = FIND_RESERVATION_BY_TICKET_NUMBER + "WHERE f.flight_number = ?";
}
