package travelAgency.repository.passenger;

import travelAgency.domain.city.City;
import travelAgency.domain.passenger.Passenger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static travelAgency.domain.passenger.PassengerBuilder.passenger;

public class PassengerSQL {
    public static final String CREATE_DELETED_TRIGGER = """
            create trigger ticket_deleted
            AFTER DELETE ON `passengers`
            FOR EACH ROW
            BEGIN
            	delete from tickets where tickets.passenger_id = old.passenger_id;
            END
            """;
    public static final String INSERT_QUERY = """
            insert into 
            passengers(passenger_id,first_name,last_name,birthday,city,address,zipcode,phone_number)
             values (?,?,?,?,?,?,?,?);
            """;
    public static final String TRIGGER_NAME = "ticket_deleted";
    public static final String DROP_TRIGGER = "DROP TRIGGER IF EXISTS " + TRIGGER_NAME;
    public static final String SELECT_PASSENGER = "select * from passengers where passenger_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM passengers";


    public static Passenger buildPassenger(ResultSet rs) throws SQLException {
        return passenger()
                .withId(rs.getString("passenger_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .withBirthday(rs.getDate("birthday").toLocalDate())
                .withZipcode(rs.getString("zipcode"))
                .ofCity(getCity(rs.getString("city")))
                .withAddress(rs.getString("address"))
                .withPhoneNumber(rs.getString("phone_number"))
                .build();

    }

    public static void fillPassengerField(Passenger passenger, PreparedStatement sql) throws SQLException {
        sql.setString(1, passenger.id());
        sql.setString(2, passenger.fName());
        sql.setString(3, passenger.lName());
        sql.setDate(4, convertToSQLDate(passenger.birthday()));
        sql.setString(5, passenger.city().name());
        sql.setString(6, passenger.address());
        sql.setString(7, passenger.zipcode());
        sql.setString(8, passenger.phoneNumber());
    }

    private static Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    private static City getCity(String city) {
        return City.valueOf(city);
    }
}
