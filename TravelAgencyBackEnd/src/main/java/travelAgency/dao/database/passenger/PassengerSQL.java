package travelAgency.dao.database.passenger;

import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;
import travelAgency.domain.vo.ResidentialAddress;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static travelAgency.domain.passenger.PassengerDtoBuilder.passenger;

public class PassengerSQL {
    public static final String CREATE_DELETED_TRIGGER = """
            create trigger ticket_deleted AFTER DELETE ON `passengers`
            FOR EACH ROW
            BEGIN
            	delete from tickets where tickets.passenger_id = old.passenger_id;
            END
            """;
    
    public static final String INSERT_PASSENGER_SQL = """
            insert into 
            passengers(passenger_id,first_name,last_name, birthday, city,address,zipcode,phone_number)
             values (?,?,?,?,?,?,?,?);
            """;
    public static final String TRIGGER_NAME
            = "ticket_deleted";
    public static final String DROP_TRIGGER
            = "DROP TRIGGER IF EXISTS " + TRIGGER_NAME;
    public static final String FIND_PASSENGER_BY_ID_SQL
            = "select * from passengers where passenger_id = ?";
    public static final String GET_ALL_PASSENGERS_SQL
            = "SELECT * FROM passengers";


    public static Passenger buildPassenger(ResultSet rs) throws SQLException {
        final PassengerId passengerId = PassengerId.of(rs.getString("passenger_id"));
        final String firstName = rs.getString("first_name");
        final String lastName = rs.getString("last_name");
        final FullName fullName = FullName.of(firstName, lastName);
        final LocalDate birthday = rs.getDate("birthday").toLocalDate();
        final String zipcode = rs.getString("zipcode");
        final String city = rs.getString("city");
        final String address = rs.getString("address");
        final Phone phoneNumber = Phone.of(rs.getString("phone_number"));
        final ResidentialAddress residentialAddress = ResidentialAddress.of(city, address, zipcode);
        return new Passenger(passengerId,fullName,birthday,residentialAddress,phoneNumber);

    }

    public static void fillOutPassengerFields(Passenger passenger, PreparedStatement sql) throws SQLException {
        sql.setString(1, passenger.passengerId().getId());
        sql.setString(2, passenger.fullName().getFirstName());
        sql.setString(3, passenger.fullName().getLastName());
        sql.setDate(4, convertToSQLDate(passenger.birthday()));
        sql.setString(5, passenger.residential().getCity());
        sql.setString(6, passenger.residential().getAddress());
        sql.setString(7, passenger.residential().getZipcode());
        sql.setString(8, passenger.phoneNumber().getNumber());
    }

    private static Date convertToSQLDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

}
