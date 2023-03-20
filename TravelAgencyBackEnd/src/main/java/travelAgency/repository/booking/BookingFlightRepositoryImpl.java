package travelAgency.repository.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.db.DbConnection;

import java.sql.*;

public class BookingFlightRepositoryImpl implements BookingFlightRepository {

    private static final String INSERT_QUERY = """
            INSERT INTO tickets
            (ticket_number,flight_number,passenger_id,number_of_tickets)
            VALUES (?,?,?,?)
            """;

    private static final String TABLE_NAME = "tickets";

    private final DbConnection db;
    private final Connection connection;

    public BookingFlightRepositoryImpl(DbConnection db) {
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

    private PreparedStatement createQuery(String query) throws SQLException {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    private void fillFlightTicket(FlightTicket flightTicket, PreparedStatement query) throws SQLException {
        query.setString(1, flightTicket.ticketNumber());
        query.setString(2, flightTicket.flightNumber());
        query.setString(3, flightTicket.passenger_id());
        query.setInt(4, flightTicket.numberOfTickets());
    }

    @Override
    public void truncate() {
        db.truncate(TABLE_NAME);
    }
}
