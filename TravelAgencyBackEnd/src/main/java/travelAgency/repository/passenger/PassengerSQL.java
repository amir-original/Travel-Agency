package travelAgency.repository.passenger;

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
}
