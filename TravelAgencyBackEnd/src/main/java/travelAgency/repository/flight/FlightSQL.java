package travelAgency.repository.flight;

public class FlightSQL {
    public static final String INSERT_QUERY = """
            INSERT INTO flights (flight_number,from_city,to_city,departure,arrival,total_capacity,price)
             VALUES (?,?,?,?,?,?,?)
            """;
    public static final String DELETE_QUERY = "DELETE FROM flights WHERE flight_number = ?";

    public static final String SELECT_ALL = "SELECT * FROM flights";
    public static final String SELECT_WHERE = "SELECT * FROM flights WHERE flight_number = ?";
    public static final String SELECT_ALL_FIND_FLIGHTS = """
            SELECT * FROM flights 
            where from_city = ? and 
                to_city = ? and
                departure = ? and
                arrival=?
            """;
}
