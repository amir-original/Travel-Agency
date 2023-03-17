package travelAgency.repository.passenger;

import travelAgency.domain.Passenger;
import travelAgency.repository.db.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class PassengerRepositoryImpl implements PassengerRepository {
    private static final String CREATE_DELETED_TRIGGER = """
            create trigger ticket_deleted
            AFTER DELETE ON `passengers`
            FOR EACH ROW
            BEGIN
            	delete from tickets where tickets.passenger_id = old.passenger_id;
            END
            """;
    private static final String INSERT_QUERY = """
            insert into 
            passengers(passenger_id,first_name,last_name,birthday,city,address,zipcode,phone_number)
             values (?,?,?,?,?,?,?,?);
            """;

    private static final String TRIGGER_NAME = "ticket_deleted";
    private static final String DROP_TRIGGER = "DROP TRIGGER IF EXISTS " + TRIGGER_NAME;

    private final DbConnection db;
    private final Connection connection;

    public PassengerRepositoryImpl(DbConnection db) {
        this.db = db;
        this.connection = db.getConnection();
        activeDeletedTrigger();
    }


    @Override
    public void save(Passenger passenger) {
        try (final PreparedStatement query = createQuery(INSERT_QUERY)) {
            fillPassengerField(passenger, query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillPassengerField(Passenger passenger, PreparedStatement sql) throws SQLException {
        sql.setString(1, passenger.id());
        sql.setString(2, passenger.fName());
        sql.setString(3, passenger.lName());
        sql.setDate(4, sqlDateOf(passenger.birthday()));
        sql.setString(5, passenger.city().name());
        sql.setString(6, passenger.address());
        sql.setString(7, passenger.zipcode());
        sql.setString(8, passenger.phoneNumber());
    }

    private Date sqlDateOf(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    private void activeDeletedTrigger() {
        dropTrigger();
        try (final PreparedStatement query = createQuery(CREATE_DELETED_TRIGGER)) {
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private void dropTrigger() {
        try (final PreparedStatement statement = createQuery(DROP_TRIGGER)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Passenger> passenger(String passengerId) {
        return Optional.empty();
    }

    public void truncate() {
        db.truncate("passengers");
    }
}
