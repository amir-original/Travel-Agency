package travelAgency.repository.booking;

public class BookingSQL {
    public static final String SELECT_ALL_JOIN = """
            SELECT t.ticket_number,t.number_of_tickets,f.*,p.* FROM tickets as t\s
                join flights as f on t.flight_number = f.flight_number
                join passengers as p on t.passenger_id = p.passenger_id
            """;

    public static final String INSERT_QUERY = """
            INSERT INTO tickets
            (ticket_number,flight_number,passenger_id,number_of_tickets)
            VALUES (?,?,?,?)
            """;

    public static final String SELECT_JOIN_WHERE = SELECT_ALL_JOIN + " where t.ticket_number = ?";
    public static final String TABLE_NAME = "tickets";
}
